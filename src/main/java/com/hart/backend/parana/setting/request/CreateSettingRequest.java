package com.hart.backend.parana.setting.request;

public class CreateSettingRequest {

    private Long userId;

    public CreateSettingRequest() {

    }

    public CreateSettingRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
