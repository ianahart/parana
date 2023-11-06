package com.hart.backend.parana.authentication;

import java.io.IOException;

import com.hart.backend.parana.passwordreset.request.ForgotPasswordRequest;
import com.hart.backend.parana.passwordreset.request.ResetPasswordRequest;
import com.hart.backend.parana.authentication.request.LoginRequest;
import com.hart.backend.parana.authentication.request.RegisterRequest;
import com.hart.backend.parana.authentication.response.LoginResponse;
import com.hart.backend.parana.authentication.response.RegisterResponse;
import com.hart.backend.parana.config.JwtService;
import com.hart.backend.parana.passwordreset.PasswordResetService;
import com.hart.backend.parana.passwordreset.response.ForgotPasswordResponse;
import com.hart.backend.parana.passwordreset.response.ResetPasswordResponse;
import com.hart.backend.parana.refreshtoken.RefreshToken;
import com.hart.backend.parana.refreshtoken.RefreshTokenService;
import com.hart.backend.parana.refreshtoken.request.RefreshTokenRequest;
import com.hart.backend.parana.refreshtoken.response.RefreshTokenResponse;
import com.hart.backend.parana.token.TokenService;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {

    @Value("${DEFAULT_TTL}")
    private Long DEFAULT_TTL;

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordResetService passwordResetService;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService,
            RefreshTokenService refreshTokenService,
            JwtService jwtService,
            TokenService tokenService,
            UserService userService,
            PasswordResetService passwordResetService) {
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(HttpServletRequest servletRequest,
            @RequestBody @Valid RegisterRequest request) {
        this.authenticationService.register(servletRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse("success"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.authenticationService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = this.refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        this.tokenService.revokeAllUserTokens(refreshToken.getUser());
        String token = this.jwtService.generateToken(refreshToken.getUser(), DEFAULT_TTL);
        this.authenticationService.saveTokenWithUser(token, refreshToken.getUser());

        return ResponseEntity.status(200).body(
                new RefreshTokenResponse(token, refreshToken.getRefreshToken()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> sendEmail(@RequestBody ForgotPasswordRequest request)
            throws IOException, TemplateException, MessagingException {
        User user = this.userService.getUserByEmail(request.getEmail());

        this.passwordResetService.deleteUserPasswordResetsById(user.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.passwordResetService.sendForgotPasswordEmail(request));

    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        this.passwordResetService.resetPassword(
                request.getPassword(),
                request.getConfirmPassword(),
                request.getPassCode(),
                request.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(new ResetPasswordResponse("success"));
    }
}
