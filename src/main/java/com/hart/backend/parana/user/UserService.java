package com.hart.backend.parana.user;

import java.security.Key;
import java.util.Optional;

import com.hart.backend.parana.user.dto.FullUserDto;
import com.hart.backend.parana.user.dto.TeacherDto;
import com.hart.backend.parana.user.dto.UserDto;
import com.hart.backend.parana.user.dto.UserPaginationDto;
import com.hart.backend.parana.util.MyUtil;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Value("${secretkey}")
    private String secretKey;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean userExistsByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.isPresent();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
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
                user.getFullName());

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

    public UserPaginationDto<TeacherDto> retrieveTeachers(int page, int pageSize, String direction, int rateParam) {
        int currentPage = MyUtil.paginate(page, direction);
        Integer rate = rateParam == 1 ? null : rateParam;
        Pageable paging = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<TeacherDto> result = this.userRepository.retrieveTeachers(paging, rate);

        return new UserPaginationDto<TeacherDto>(result.getContent(), currentPage, pageSize, result.getTotalPages(),
                direction, result.getTotalElements());

    }

    public UserPaginationDto<UserDto> retrieveUsers(int page, int pageSize, String direction) {
        int currentPage = MyUtil.paginate(page, direction);
        Pageable paging = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<UserDto> result = this.userRepository.retrieveUsers(paging);

        return new UserPaginationDto<UserDto>(result.getContent(), currentPage, pageSize, result.getTotalPages(),
                direction, result.getTotalElements());

    }
}
