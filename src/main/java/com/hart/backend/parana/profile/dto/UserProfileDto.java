package com.hart.backend.parana.profile.dto;

import com.hart.backend.parana.user.Role;

public class UserProfileDto {
    private String aboutLesson;
    private String firstName;
    private String fullName;
    private Role role;
    private Long userId;
    private Long id;
    private String bio;
    private String city;
    private String homeMountain;
    private String stance;
    private String state;
    private String terrain;
    private String travelUpTo;
    private Integer yearsSnowboarding;
    private String avatarUrl;

    public UserProfileDto() {

    }

    public UserProfileDto(
            String aboutLesson,
            String firstName,
            String fullName,
            Role role,
            Long userId,
            Long id,
            String bio,
            String city,
            String homeMountain,
            String stance,
            String state,
            String terrain,
            String travelUpTo,
            Integer yearsSnowboarding,
            String avatarUrl) {
        this.aboutLesson = aboutLesson;
        this.firstName = firstName;
        this.fullName = fullName;
        this.role = role;
        this.userId = userId;
        this.id = id;
        this.bio = bio;
        this.city = city;
        this.homeMountain = homeMountain;
        this.stance = stance;
        this.state = state;
        this.terrain = terrain;
        this.travelUpTo = travelUpTo;
        this.yearsSnowboarding = yearsSnowboarding;
        this.avatarUrl = avatarUrl;
    }

    public String getAboutLesson() {
        return aboutLesson;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getStance() {
        return stance;
    }

    public String getTerrain() {
        return terrain;
    }

    public Role getRole() {
        return role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getTravelUpTo() {
        return travelUpTo;
    }

    public String getHomeMountain() {
        return homeMountain;
    }

    public Integer getYearsSnowboarding() {
        return yearsSnowboarding;
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

    public void setStance(String stance) {
        this.stance = stance;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setTravelUpTo(String travelUpTo) {
        this.travelUpTo = travelUpTo;
    }

    public void setHomeMountain(String homeMountain) {
        this.homeMountain = homeMountain;
    }

    public void setYearsSnowboarding(Integer yearsSnowboarding) {
        this.yearsSnowboarding = yearsSnowboarding;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAboutLesson(String aboutLesson) {
        this.aboutLesson = aboutLesson;
    }
}
