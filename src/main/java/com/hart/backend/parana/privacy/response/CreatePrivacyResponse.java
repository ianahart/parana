package com.hart.backend.parana.privacy.response;

public class CreatePrivacyResponse {

    private String message;

    public CreatePrivacyResponse() {

    }

    public CreatePrivacyResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
