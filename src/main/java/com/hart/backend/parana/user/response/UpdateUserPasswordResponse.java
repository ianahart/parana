package com.hart.backend.parana.user.response;

public class UpdateUserPasswordResponse {

    private String message;

    private String data;

    public UpdateUserPasswordResponse() {

    }

    public UpdateUserPasswordResponse(String message, String data) {
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
