package com.hart.backend.parana.passwordreset.response;

public class ForgotPasswordResponse {

    private String message;

    public ForgotPasswordResponse() {

    }

    public ForgotPasswordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
