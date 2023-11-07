package com.hart.backend.parana.setting.response;

public class UpdateRememberMeResponse {

    private String message;

    private String data;

    UpdateRememberMeResponse() {

    }

    public UpdateRememberMeResponse(String message, String data) {

        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(String data) {
        this.data = data;
    }
}
