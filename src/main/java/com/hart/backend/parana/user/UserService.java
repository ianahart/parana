package com.hart.backend.parana.user;

import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hart.backend.parana.user.dto.FullUserDto;
import com.hart.backend.parana.user.dto.SearchTeacherDto;
import com.hart.backend.parana.user.dto.TeacherDto;
import com.hart.backend.parana.user.dto.UserDto;
import com.hart.backend.parana.user.dto.UserPaginationDto;
import com.hart.backend.parana.user.dto.UserSuggestionDto;
import com.hart.backend.parana.user.request.UpdateUserPasswordRequest;
import com.hart.backend.parana.util.MyUtil;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.connection.ConnectionService;
import com.hart.backend.parana.favorite.FavoriteService;
import com.hart.backend.parana.geocode.GeocodeService;
import com.hart.backend.parana.setting.SettingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${secretkey}")
    private String secretKey;

    private final UserRepository userRepository;
    private final GeocodeService geocodeService;
    private final FavoriteService favoriteService;
    private final ConnectionService connectionService;
    private final PasswordEncoder passwordEncoder;
    private final SettingService settingService;

    @Autowired
    public UserService(UserRepository userRepository, GeocodeService geocodeService,
            @Lazy FavoriteService favoriteService,
            @Lazy ConnectionService connectionService,
            PasswordEncoder passwordEncoder,
            @Lazy SettingService settingService) {
        this.userRepository = userRepository;
        this.geocodeService = geocodeService;
        this.favoriteService = favoriteService;
        this.connectionService = connectionService;
        this.passwordEncoder = passwordEncoder;
        this.settingService = settingService;
    }

    public boolean userExistsByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.isPresent();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public User getUserById(Long userId) {
        String error = String.format("User with id %d is not found", userId);
        return this.userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.info(error);
                    return new NotFoundException(error);
                });
    }

    private Claims extractUserIdFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public FullUserDto syncUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.contains("Bearer ")) {
            throw new BadRequestException("Token missing from request header");
        }

        Claims claims = extractUserIdFromToken(token.split("Bearer ")[1]);

        User user = getUserByEmail(claims.getSubject());
        return new FullUserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getAbbreviation(),
                user.getLoggedIn(),
                user.getProfile().getId(),
                user.getProfile().getAvatarUrl(),
                user.getFullName(),
                user.getSetting().getId());

    }

    public User getCurrentlyLoggedInUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String username = ((UserDetails) principal).getUsername();
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Current user was not found"));
        return user;
    }

    private Map<String, Double> getUserLocation() {
        User user = getCurrentlyLoggedInUser();
        return this.geocodeService.geocode(user.getProfile().getCity(), user.getProfile().getState());
    }

    private List<TeacherDto> AddExtraFieldsToTeacher(List<TeacherDto> teachers) {
        User user = getCurrentlyLoggedInUser();
        for (TeacherDto teacher : teachers) {
            teacher.setIsNewTeacher(isTeacherNew(teacher.getCreatedAt()));
            teacher.setFavorite(this.favoriteService.getFavorite(user.getId(), teacher.getUserId()));
        }

        return teachers;
    }

    public UserPaginationDto<TeacherDto> retrieveTeachers(int page, int pageSize, String direction, int rateParam,
            Double distanceParam) {
        int currentPage = MyUtil.paginate(page, direction);

        Integer rate = rateParam == 1 ? null : rateParam;
        Double distance = distanceParam == 1 ? null : distanceParam;

        Pageable paging = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Map<String, Double> coords = getUserLocation();
        Page<TeacherDto> result = distance != null
                ? this.userRepository.findTeachersWithinDistance(coords.get("latitude"), coords.get("longitude"),
                        distance, rate, paging)
                : this.userRepository.retrieveTeachers(paging, rate);

        List<TeacherDto> teachers = AddExtraFieldsToTeacher(result.getContent());
        return new UserPaginationDto<TeacherDto>(teachers, currentPage, pageSize, result.getTotalPages(),
                direction, result.getTotalElements());

    }

    private boolean isTeacherNew(Timestamp timestamp) {
        long createdAtInSeconds = (timestamp.getTime() / 1000L);
        long nowInSeconds = Instant.now().getEpochSecond();
        final int sixMonths = 15768000;
        return nowInSeconds - createdAtInSeconds < sixMonths;

    }

    public UserPaginationDto<UserDto> retrieveUsers(int page, int pageSize, String direction) {
        int currentPage = MyUtil.paginate(page, direction);
        Pageable paging = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<UserDto> result = this.userRepository.retrieveUsers(paging);

        return new UserPaginationDto<UserDto>(result.getContent(), currentPage, pageSize, result.getTotalPages(),
                direction, result.getTotalElements());

    }

    public UserPaginationDto<SearchTeacherDto> searchTeachers(String searchTerm, int page, int pageSize,
            String direction) {
        int currentPage = MyUtil.paginate(page, direction);
        Pageable paging = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<SearchTeacherDto> result = this.userRepository.searchTeachers(searchTerm.toLowerCase(), paging);

        return new UserPaginationDto<SearchTeacherDto>(result.getContent(), currentPage, pageSize,
                result.getTotalPages(),
                direction, result.getTotalElements());

    }

    private List<UserSuggestionDto> getNonConnectedUsers(User currentUser) {
        return this.userRepository
                .getUserSuggestions()
                .stream()
                .filter(u -> !this.connectionService.isConnected(currentUser.getId(),
                        u.getTeacherId()) && !this.connectionService.isPending(currentUser.getId(), u.getTeacherId()))
                .toList();
    }

    public List<UserSuggestionDto> getUserSuggestions() {
        User currentUser = getCurrentlyLoggedInUser();

        List<UserSuggestionDto> nonConnectedUsers = new ArrayList<UserSuggestionDto>(getNonConnectedUsers(currentUser));

        List<String> currentUserTerrain = currentUser.getProfile().getTerrain() == null ? new ArrayList<>()
                : Arrays.asList(currentUser.getProfile().getTerrain().split(","));

        for (int i = 0; i < nonConnectedUsers.size(); i++) {

            List<String> teacherTerrain = nonConnectedUsers.get(i).getTerrain() == null ? new ArrayList<>()
                    : Arrays.asList(nonConnectedUsers.get(i).getTerrain().split(","));

            List<String> maxList = teacherTerrain.size() > currentUserTerrain.size() ? teacherTerrain
                    : currentUserTerrain;

            List<String> terrainInCommon = new ArrayList<String>();

            for (int j = 0; j < maxList.size(); j++) {

                if (teacherTerrain.size() > currentUserTerrain.size()) {

                    if (currentUserTerrain.contains(teacherTerrain.get(j))) {
                        terrainInCommon.add(teacherTerrain.get(j));
                    }
                } else {
                    if (teacherTerrain.contains(currentUserTerrain.get(j))) {
                        terrainInCommon.add(currentUserTerrain.get(j));
                    }
                }
            }

            nonConnectedUsers.get(i).setTerrainInCommon(terrainInCommon);
        }
        nonConnectedUsers.removeIf(u -> u.getTerrainInCommon().size() <= 0);
        return nonConnectedUsers;
    }

    private boolean currentPasswordDifferent(String password, User user) {
        return !(this.passwordEncoder.matches(password, user.getPassword()));
    }

    private void validatePasswords(String password, String confirmPassword) {

        if (!MyUtil.validatePassword(password)) {
            throw new BadRequestException("Password must include 1 lower, 1 upper, 1 special, and 1 digit char");
        }

        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("Passwords do not match");
        }
    }

    private void checkPassword(String oldPassword, String hashedOldPassword) {
        if (!this.passwordEncoder.matches(oldPassword, hashedOldPassword)) {
            throw new BadRequestException("Your password is incorrect");
        }
    }

    public String updatePassword(Long userId, UpdateUserPasswordRequest request) {
        User user = getUserById(userId);

        checkPassword(request.getOldPassword(), user.getPassword());

        if (!currentPasswordDifferent(request.getPassword(), user)) {
            throw new BadRequestException("Your new password cannot be the same as your old password");
        }

        validatePasswords(request.getPassword(), request.getConfirmPassword());

        user.setPassword(this.passwordEncoder.encode(request.getPassword()));

        this.userRepository.save(user);

        return this.settingService.updatePasswordDate(user.getSetting().getId());

    }
}
