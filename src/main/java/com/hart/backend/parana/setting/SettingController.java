package com.hart.backend.parana.setting;

import com.hart.backend.parana.setting.request.UpdateSettingRequest;
import com.hart.backend.parana.setting.response.GetSettingResponse;
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

    @GetMapping("/{settingId}")
    public ResponseEntity<GetSettingResponse> getSettings(@PathVariable("settingId") Long settingId) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new GetSettingResponse("success", this.settingService.getSettings(settingId)));
    }

    @PatchMapping("/{settingId}")
    public ResponseEntity<UpdateSettingResponse> updateSetting(HttpServletRequest servletRequest,
            @RequestBody UpdateSettingRequest request,
            @PathVariable("settingId") Long settingId) {
        this.settingService.updateSetting(servletRequest, request, settingId);
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateSettingResponse("success"));
    }

}
