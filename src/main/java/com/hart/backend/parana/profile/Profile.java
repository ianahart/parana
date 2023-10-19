package com.hart.backend.parana.profile;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hart.backend.parana.user.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @SequenceGenerator(name = "profile_sequence", sequenceName = "profile_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_sequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_At")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "avatar_filename", length = 300)
    private String avatarFilename;

    @Column(name = "avatar_url", length = 400)
    private String avatarUrl;

    @Column(name = "bio", length = 400)
    private String bio;

    @Column(name = "about_lesson", length = 400)
    private String aboutLesson;

    @Column(name = "city")
    private String city;

    @Column(name = "first_lesson_free")
    private Boolean firstLessonFree;

    @Column(name = "home_mountain")
    private String homeMountain;

    @Column(name = "per_hour")
    private String perHour;

    @Column(name = "stance")
    private String stance;

    @Column(name = "state")
    private String state;

    @Column(name = "tags")
    private String tags;

    @Column(name = "terrain")
    private String terrain;

    @Column(name = "travel_up_to")
    private String travelUpTo;

    @Column(name = "years_snowboarding")
    private Integer yearsSnowboarding;

    @JsonIgnore
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private User user;

    public Profile() {

    }

    public Profile(
            Long id,
            Timestamp createdAt,
            Timestamp updatedAt,
            String avatarFilename,
            String avatarUrl,
            String bio,
            String aboutLesson,
            String city,
            Boolean firstLessonFree,
            String homeMountain,
            String perHour,
            String stance,
            String state,
            String tags,
            String terrain,
            String travelUpTo,
            Integer yearsSnowboarding) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.avatarFilename = avatarFilename;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.aboutLesson = aboutLesson;
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
    }

    public Long getId() {
        return id;
    }

    public String getAboutLesson() {
        return aboutLesson;
    }

    public String getBio() {
        return bio;
    }

    public User getUser() {
        return user;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public String getAvatarFilename() {
        return avatarFilename;
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

    public String getTravelUpTo() {
        return travelUpTo;
    }

    public String getTerrain() {
        return terrain;
    }

    public Boolean getFirstLessonFree() {
        return firstLessonFree;
    }

    public String getHomeMountain() {
        return homeMountain;
    }

    public Integer getYearsSnowboarding() {
        return yearsSnowboarding;
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

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAvatarFilename(String avatarFilename) {
        this.avatarFilename = avatarFilename;
    }

    public void setAboutLesson(String aboutLesson) {
        this.aboutLesson = aboutLesson;
    }

}
