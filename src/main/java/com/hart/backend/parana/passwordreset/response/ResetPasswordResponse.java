package com.hart.backend.parana.passwordreset.response;

public class ResetPasswordResponse {

    private String message;

    public ResetPasswordResponse() {

    }

    public ResetPasswordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
