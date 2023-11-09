package com.hart.backend.parana.setting;

import com.hart.backend.parana.setting.request.UpdateRememberMeRequest;
import com.hart.backend.parana.setting.request.UpdateSettingNotificationRequest;
import com.hart.backend.parana.setting.request.UpdateSettingRequest;
import com.hart.backend.parana.setting.response.GetSettingResponse;
import com.hart.backend.parana.setting.response.UpdateRememberMeResponse;
import com.hart.backend.parana.setting.response.UpdateSettingNotificationResponse;
import com.hart.backend.parana.setting.response.UpdateSettingResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/settings")
public class SettingController {

    private final SettingService settingService;

    @Autowired()
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @PatchMapping("/{settingId}/notifications")
    public ResponseEntity<UpdateSettingNotificationResponse> updateNotifications(
            @PathVariable("settingId") Long settingId, @RequestBody UpdateSettingNotificationRequest request) {
        this.settingService.updateNotifications(settingId, request.getNotifications());
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateSettingNotificationResponse("success"));
    }

    @GetMapping("/{settingId}")
    public ResponseEntity<GetSettingResponse> getSettings(@PathVariable("settingId") Long settingId) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new GetSettingResponse("success", this.settingService.getSettings(settingId)));
    }

    @PatchMapping("/{settingId}/remember-me")
    public ResponseEntity<UpdateRememberMeResponse> updateRememberMe(@PathVariable("settingId") Long settingId,
            @RequestBody UpdateRememberMeRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(new UpdateRememberMeResponse("success",
                this.settingService.updateRememberMe(settingId, request.getRememberMe())));
    }

    @PatchMapping("/{settingId}")
    public ResponseEntity<UpdateSettingResponse> updateSetting(HttpServletRequest servletRequest,
            @RequestBody UpdateSettingRequest request,
            @PathVariable("settingId") Long settingId) {
        this.settingService.updateSetting(servletRequest, request, settingId);
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateSettingResponse("success"));
    }

}
