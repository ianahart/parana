package com.hart.backend.parana.setting.response;

public class CreateSettingResponse {

    private String message;

    private Long data;

    public CreateSettingResponse() {

    }

    public CreateSettingResponse(String message, Long data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Long getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Long data) {
        this.data = data;
    }
}
