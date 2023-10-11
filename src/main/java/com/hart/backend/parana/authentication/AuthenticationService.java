package com.hart.backend.parana.authentication;

import java.util.Optional;

import com.hart.backend.parana.authentication.request.RegisterRequest;
import com.hart.backend.parana.authentication.response.RegisterResponse;
import com.hart.backend.parana.config.JwtService;
import com.hart.backend.parana.profile.ProfileService;
import com.hart.backend.parana.refreshtoken.RefreshTokenRepository;
import com.hart.backend.parana.refreshtoken.RefreshTokenService;
import com.hart.backend.parana.token.TokenRepository;
import com.hart.backend.parana.token.TokenService;
import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserRepository;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;
import com.hart.backend.parana.advice.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;
    private final UserService userService;

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
            UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.refreshTokenService = refreshTokenService;
        this.tokenService = tokenService;
        this.userService = userService;
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

    public RegisterResponse register(RegisterRequest request) {
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
                this.profileService.createProfile());
        this.userRepository.save(user);
        return new RegisterResponse("User created");
    }

}
