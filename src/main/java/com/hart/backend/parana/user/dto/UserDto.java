package com.hart.backend.parana.user.dto;

public class UserDto {

    private String avatarUrl;
    private String bio;
    private Long userId;
    private Long profileId;
    private String firstName;
    private String city;
    private String state;

    public UserDto(
            String avatarUrl,
            String bio,
            Long userId,
            Long profileId,
            String firstName,
            String city,
            String state) {

        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.userId = userId;
        this.profileId = profileId;
        this.firstName = firstName;
        this.city = city;
        this.state = state;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
