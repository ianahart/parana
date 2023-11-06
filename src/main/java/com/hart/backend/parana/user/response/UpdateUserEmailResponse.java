package com.hart.backend.parana.user.response;

public class UpdateUserEmailResponse {

    private String message;

    public UpdateUserEmailResponse() {

    }

    public UpdateUserEmailResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
