package com.hart.backend.parana.setting.response;

public class UpdateSettingResponse {

    private String message;

    public UpdateSettingResponse() {

    }

    public UpdateSettingResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
