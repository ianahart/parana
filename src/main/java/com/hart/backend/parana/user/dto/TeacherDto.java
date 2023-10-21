package com.hart.backend.parana.user.dto;

public class TeacherDto {

    private Boolean firstLessonFree;
    private String avatarUrl;
    private String bio;
    private Long userId;
    private Long profileId;
    private String perHour;
    private String firstName;
    private String city;
    private String state;

    public TeacherDto(
            Boolean firstLessonFree,
            String avatarUrl,
            String bio,
            Long userId,
            Long profileId,
            String perHour,
            String firstName,
            String city,
            String state) {
        this.firstLessonFree = firstLessonFree;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.userId = userId;
        this.profileId = profileId;
        this.perHour = perHour;
        this.firstName = firstName;
        this.city = city;
        this.state = state;
    }

    public Boolean getFirstLessonFree() {
        return firstLessonFree;
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

    public String getPerHour() {
        return perHour;
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

    public void setPerHour(String perHour) {
        this.perHour = perHour;
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

    public void setFirstLessonFree(Boolean firstLessonFree) {
        this.firstLessonFree = firstLessonFree;
    }
}