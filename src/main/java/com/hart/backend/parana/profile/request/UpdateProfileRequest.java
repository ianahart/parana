package com.hart.backend.parana.profile.request;

import jakarta.validation.constraints.Size;

public class UpdateProfileRequest {

    @Size(min = 0, max = 400, message = "Bio should be between 1 and 400 characters")
    private String bio;

    @Size(min = 0, max = 200, message = "City should be between 1 and 200 characters")
    private String city;

    private Boolean firstLessonFree;

    @Size(min = 0, max = 200, message = "Home mountain should be between 1 and 200 characters")
    private String homeMountain;

    @Size(min = 0, max = 40, message = "Per hour should be between 1 and 40 characters")
    private String perHour;

    @Size(min = 0, max = 20, message = "Stance should be between 1 and 20 characters")
    private String stance;

    @Size(min = 0, max = 50, message = "State should be between 1 and 50 characters")
    private String state;

    @Size(min = 0, max = 400, message = "Tags should be bewtween 1 and 400 characters")
    private String tags;

    @Size(min = 0, max = 400, message = "Terrain should be between 1 and 400 characters")
    private String terrain;

    @Size(min = 0, max = 100, message = "Travel up to should be between 1 and 100 characters")
    private String travelUpTo;

    private Integer yearsSnowboarding;

    private String formType;

    public UpdateProfileRequest() {

    }

    public UpdateProfileRequest(
            String bio,
            String city,
            Boolean firstLessonFree,
            String homeMountain,
            String perHour,
            String stance,
            String state,
            String tags,
            String terrain,
            String travelUpTo,
            Integer yearsSnowboarding,
            String formType

    ) {

        this.bio = bio;
        this.city = city;
        this.firstLessonFree = firstLessonFree;
        this.homeMountain = homeMountain;
        this.perHour = perHour;
        this.stance = stance;
        this.state = state;
        this.tags = tags;
        this.terrain = terrain;
        this.travelUpTo = travelUpTo;
        this.yearsSnowboarding = yearsSnowboarding;
        this.formType = formType;
    }

    public String getBio() {
        return bio;
    }

    public String getCity() {
        return city;
    }

    public String getTags() {
        return tags;
    }

    public String getStance() {
        return stance;
    }

    public String getState() {
        return state;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getPerHour() {
        return perHour;
    }

    public String getTravelUpTo() {
        return travelUpTo;
    }

    public String getFormType() {
        return formType;
    }

    public String getHomeMountain() {
        return homeMountain;
    }

    public Boolean getFirstLessonFree() {
        return firstLessonFree;
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

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStance(String stance) {
        this.stance = stance;
    }

    public void setPerHour(String perHour) {
        this.perHour = perHour;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public void setTravelUpTo(String travelUpTo) {
        this.travelUpTo = travelUpTo;
    }

    public void setHomeMountain(String homeMountain) {
        this.homeMountain = homeMountain;
    }

    public void setFirstLessonFree(Boolean firstLessonFree) {
        this.firstLessonFree = firstLessonFree;
    }

    public void setYearsSnowboarding(Integer yearsSnowboarding) {
        this.yearsSnowboarding = yearsSnowboarding;
    }
}
