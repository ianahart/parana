package com.hart.backend.parana.setting;

import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import java.sql.Timestamp;

import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.config.JwtService;
import com.hart.backend.parana.setting.dto.SettingDto;
import com.hart.backend.parana.setting.request.UpdateSettingRequest;
import com.hart.backend.parana.token.TokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SettingService {

    @Value("${REMEMBER_ME_TTL}")
    private Long REMEMBER_ME_TTL;

    Logger logger = LoggerFactory.getLogger(SettingService.class);

    private final SettingRepository settingRepository;

    private final UserService userService;

    private final TokenService tokenService;

    private final JwtService jwtService;

    @Autowired
    public SettingService(
            SettingRepository settingRepository,
            UserService userService,
            TokenService tokenService,
            JwtService jwtService) {
        this.settingRepository = settingRepository;
        this.userService = userService;
        this.tokenService = tokenService;
        this.jwtService = jwtService;
    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    public Setting createSetting(HttpServletRequest request) {

        Setting setting = new Setting(
                false,
                false,
                false,
                false,
                getClientIp(request),
                true);
        this.settingRepository.save(setting);
        return setting;
    }

    public Setting getSettingById(Long settingId) {
        return this.settingRepository.findById(settingId).orElseThrow(() -> {
            String error = String.format("Setting with the id %d was not found");
            logger.info(error);
            throw new NotFoundException(error);
        });
    }

    public void updateSetting(HttpServletRequest servletRequest, UpdateSettingRequest request, Long settingId) {
        Setting setting = getSettingById(settingId);

        setting.setIpAddress(getClientIp(servletRequest));
        setting.setRememberMe(request.getRememberMe());
        setting.setBlockPosts(request.getBlockPosts());
        setting.setBlockComments(request.getBlockComments());
        setting.setBlockMessages(request.getBlockMessages());

        this.settingRepository.save(setting);

    }

    public String updatePasswordDate(Long settingId) {
        Setting setting = this.getSettingById(settingId);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        setting.setPasswordUpdatedOn(timestamp);

        this.settingRepository.save(setting);

        return MyUtil.createDate(timestamp);
    }

    public SettingDto getSettings(Long settingId) {
        SettingDto setting = this.settingRepository.getSettings(settingId);

        if (setting.getPasswordUpdatedOn() != null) {
            setting.setUpdatedFormattedDate(MyUtil.createDate(setting.getPasswordUpdatedOn()));
        }

        if (setting.getEmailUpdatedOn() != null) {
            setting.setEmailUpdatedFormattedDate(MyUtil.createDate(setting.getEmailUpdatedOn()));
        }
        return setting;
    }

    public void updateEmailDate(Long settingId) {
        Setting setting = this.getSettingById(settingId);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        setting.setEmailUpdatedOn(timestamp);

        this.settingRepository.save(setting);

    }

    public String updateRememberMe(Long settingId, Boolean rememberMe) {
        Setting setting = getSettingById(settingId);
        setting.setRememberMe(rememberMe);

        this.settingRepository.save(setting);
        if (setting.getRememberMe()) {
            User user = setting.getUser();
            this.tokenService.revokeAllUserTokens(user);
            String token = this.jwtService.generateToken(user, REMEMBER_ME_TTL);
            this.tokenService.saveTokenWithUser(token, user);
            return token;
        }
        return "";

    }

    public void updateNotifications(Long settingId, Boolean notifications) {
        Setting setting = getSettingById(settingId);

        setting.setNotifications(notifications);

        this.settingRepository.save(setting);
    }
}
