package com.hart.backend.parana.profile.response;

public class ProfilePhotoResponse {

    private String message;
    private String avatarUrl;

    public ProfilePhotoResponse() {

    }

    public ProfilePhotoResponse(String message, String avatarUrl) {
        this.message = message;
        this.avatarUrl = avatarUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
