package com.hart.backend.parana.setting.response;

public class UpdateSettingNotificationResponse {

    private String message;

    public UpdateSettingNotificationResponse() {

    }

    public UpdateSettingNotificationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
