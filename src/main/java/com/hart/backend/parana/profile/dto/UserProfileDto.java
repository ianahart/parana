package com.hart.backend.parana.profile.dto;

public class UserProfileDto {
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
            String bio,
            String city,
            String homeMountain,
            String stance,
            String state,
            String terrain,
            String travelUpTo,
            Integer yearsSnowboarding,
            String avatarUrl) {
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
}
