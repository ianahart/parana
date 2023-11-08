package com.hart.backend.parana.privacy.response;

public class UpdatePrivacyResponse {

    private String message;

    public UpdatePrivacyResponse() {

    }

    public UpdatePrivacyResponse(String message) {
        this.message = message;
    }

    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
