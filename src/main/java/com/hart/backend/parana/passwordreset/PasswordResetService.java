package com.hart.backend.parana.passwordreset;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.config.JwtService;
import com.hart.backend.parana.passwordreset.request.ForgotPasswordRequest;
import com.hart.backend.parana.passwordreset.response.ForgotPasswordResponse;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class PasswordResetService {

    private final JwtService jwtService;
    private final Configuration configuration;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;

    @Autowired
    public PasswordResetService(
            JwtService jwtService,
            Configuration configuration,
            JavaMailSender javaMailSender,
            UserRepository userRepository,
            PasswordResetRepository passwordResetRepository) {

        this.jwtService = jwtService;
        this.configuration = configuration;
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.passwordResetRepository = passwordResetRepository;
    }

    @Value("${emailsender}")
    private String sender;

    public void deleteUserPasswordResetsById(Long id) {
        this.passwordResetRepository.deleteUserPasswordResetsById(id);
    }

    private void savePasswordReset(User user, String code, String token) {
        this.passwordResetRepository.save(new PasswordReset(user, code, token));
    }

    public ForgotPasswordResponse sendForgotPasswordEmail(ForgotPasswordRequest request)
            throws MessagingException, IOException, TemplateException {

        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(sender);
        helper.setSubject("Reset Your Password");

        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("A user with this email does not exist."));

        helper.setTo(request.getEmail());
        String emailContent = getEmailContent(user);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);

        return new ForgotPasswordResponse("Email sent successfully...");
    }

    String getEmailContent(User user) throws IOException, TemplateException {
        String token = this.jwtService.generateToken(user);
        int min = 10000;
        int max = 20000;
        String code = String.valueOf(new Random().nextInt((max - min) + 1) + min);
        savePasswordReset(user, code, token);

        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("token", token);
        model.put("code", code);
        this.configuration.getTemplate("forgot-password-email.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
