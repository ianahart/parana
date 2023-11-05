package com.hart.backend.parana.setting;

import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.setting.request.UpdateSettingRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SettingService {

    Logger logger = LoggerFactory.getLogger(SettingService.class);

    private final SettingRepository settingRepository;

    private final UserService userService;

    @Autowired
    public SettingService(
            SettingRepository settingRepository,
            UserService userService) {
        this.settingRepository = settingRepository;
        this.userService = userService;
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

    public Setting createSetting() {
        Setting setting = new Setting();
        this.settingRepository.save(setting);
        return setting;
    }

    private Setting getSettingById(Long settingId) {
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

}
