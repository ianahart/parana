package com.hart.backend.parana.authentication;

import com.hart.backend.parana.authentication.request.LoginRequest;
import com.hart.backend.parana.authentication.request.RegisterRequest;
import com.hart.backend.parana.authentication.response.LoginResponse;
import com.hart.backend.parana.authentication.response.RegisterResponse;
import com.hart.backend.parana.config.JwtService;
import com.hart.backend.parana.profile.ProfileService;
import com.hart.backend.parana.refreshtoken.RefreshToken;
import com.hart.backend.parana.refreshtoken.RefreshTokenService;
import com.hart.backend.parana.setting.SettingService;
import com.hart.backend.parana.token.Token;
import com.hart.backend.parana.token.TokenRepository;
import com.hart.backend.parana.token.TokenService;
import com.hart.backend.parana.token.TokenType;
import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserRepository;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.user.dto.FullUserDto;
import com.hart.backend.parana.util.MyUtil;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.advice.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {

    @Value("${DEFAULT_TTL}")
    private Long DEFAULT_TTL;

    @Value("${REMEMBER_ME_TTL}")
    private Long REMEMBER_ME_TTL;

    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;
    private final UserService userService;
    private final SettingService settingService;

    @Autowired
    public AuthenticationService(
            PasswordEncoder passwordEncoder,
            ProfileService profileService,
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            TokenRepository tokenRepository,
            RefreshTokenService refreshTokenService,
            TokenService tokenService,
            UserService userService,
            SettingService settingService) {
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.refreshTokenService = refreshTokenService;
        this.tokenService = tokenService;
        this.userService = userService;
        this.settingService = settingService;
    }

    private void validateRegistration(RegisterRequest request) {
        if (this.userService.userExistsByEmail(request.getEmail())) {
            throw new BadRequestException("A user with this email already exists");
        }

        if (!MyUtil.validatePassword(request.getPassword())) {

            throw new BadRequestException(
                    "Password must contain 1 letter, 1 number, 1 special char, and 1 uppercase letter");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }
    }

    public RegisterResponse register(HttpServletRequest servletRequest, RegisterRequest request) {
        validateRegistration(request);

        String firstName = MyUtil.capitalize(request.getFirstName());
        String lastName = MyUtil.capitalize(request.getLastName());
        User user = new User(
                request.getEmail(),
                firstName,
                lastName,
                String.format("%s %s", firstName, lastName),
                request.getRole().equals("USER") ? Role.USER : Role.TEACHER,
                false,
                this.profileService.createProfile(),
                this.passwordEncoder.encode(request.getPassword()),
                this.settingService.createSetting(servletRequest));
        this.userRepository.save(user);
        return new RegisterResponse("User created");
    }

    public void saveTokenWithUser(String token, User user) {
        Token tokenToSave = new Token(token, TokenType.BEARER, false, false, user);
        this.tokenRepository.save(tokenToSave);

    }

    private FullUserDto updateAuthUser(User user, String jwtToken) {

        user.setLoggedIn(true);

        this.userRepository.save(user);
        this.saveTokenWithUser(jwtToken, user);

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

    public LoginResponse login(LoginRequest request) {

        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

        } catch (BadCredentialsException e) {
            System.out.println(e);
            throw new ForbiddenException("Credentials are invalid");
        }
        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found by email."));

        Long TTL = request.getRememberMe() ? REMEMBER_ME_TTL : DEFAULT_TTL;

        this.settingService.updateRememberMe(user.getSetting().getId(), request.getRememberMe());
        String jwtToken = this.jwtService.generateToken(user, TTL);

        this.tokenService.revokeAllUserTokens(user);
        FullUserDto userDto = this.updateAuthUser(user, jwtToken);
        RefreshToken refreshToken = this.refreshTokenService.generateRefreshToken(user.getId());

        return new LoginResponse(userDto, jwtToken, refreshToken.getRefreshToken());
    }

}
