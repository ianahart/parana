package com.hart.backend.parana.profile.dto;

public class ProfileDto<T> {

    private T profile;

    public ProfileDto() {

    }

    public ProfileDto(T profile) {
        this.profile = profile;
    }

    public T getProfile() {
        return profile;
    }

    public void setProfile(T profile) {
        this.profile = profile;
    }
}
