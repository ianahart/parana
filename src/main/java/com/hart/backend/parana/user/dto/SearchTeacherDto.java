package com.hart.backend.parana.user.dto;

public class SearchTeacherDto {

    private Long id;
    private Long profileId;
    private String avatarUrl;
    private String fullName;
    private String firstName;
    private String city;
    private String state;

    public SearchTeacherDto() {

    }

    public SearchTeacherDto(
            Long id,
            Long profileId,
            String avatarUrl,
            String fullName,
            String firstName,
            String city,
            String state) {

        this.id = id;
        this.profileId = profileId;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.firstName = firstName;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }
}
