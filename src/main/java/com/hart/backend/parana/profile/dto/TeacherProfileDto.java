package com.hart.backend.parana.profile.dto;

import java.sql.Timestamp;

import com.hart.backend.parana.user.Role;

public class TeacherProfileDto {

    private String aboutLesson;
    private String firstName;
    private String fullName;
    private Role role;
    private Long userId;
    private Long id;
    private String bio;
    private String city;
    private Boolean firstLessonFree;
    private String homeMountain;
    private String perHour;
    private String stance;
    private String state;
    private String tags;
    private String terrain;
    private String travelUpTo;
    private Integer yearsSnowboarding;
    private String avatarUrl;
    private Boolean isNewTeacher;
    private Timestamp createdAt;

    public TeacherProfileDto() {

    }

    public TeacherProfileDto(
            String aboutLesson,
            String firstName,
            String fullName,
            Role role,
            Long userId,
            Long id,
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
            String avatarUrl,
            Timestamp createdAt) {
        this.aboutLesson = aboutLesson;
        this.firstName = firstName;
        this.fullName = fullName;
        this.role = role;
        this.userId = userId;
        this.id = id;
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
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
    }

    public String getAboutLesson() {
        return aboutLesson;
    }

    public String getFirstName() {
        return firstName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public Role getRole() {
        return role;
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

    public String getTags() {
        return tags;
    }

    public String getState() {
        return state;
    }

    public String getStance() {
        return stance;
    }

    public String getPerHour() {
        return perHour;
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

    public Boolean getFirstLessonFree() {
        return firstLessonFree;
    }

    public Boolean getIsNewTeacher() {
        return isNewTeacher;
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

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public void setIsNewTeacher(Boolean isNewTeacher) {
        this.isNewTeacher = isNewTeacher;
    }
}
