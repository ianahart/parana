package com.hart.backend.parana.recommendation.dto;

import java.sql.Timestamp;

public class RecommendationDto {

    private String firstName;

    private String fullName;

    private String avatarUrl;

    private Long id;

    private Long teacherId;

    private Long authorId;

    private String words;

    private String recommendation;

    private Timestamp createdAt;

    private String date;

    public RecommendationDto() {

    }

    public RecommendationDto(
            String firstName,
            String fullName,
            String avatarUrl,
            Long id,
            Long teacherId,
            Long authorId,
            String words,
            String recommendation,
            Timestamp createdAt) {

        this.firstName = firstName;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.id = id;
        this.teacherId = teacherId;
        this.authorId = authorId;
        this.words = words;
        this.recommendation = recommendation;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getWords() {
        return words;
    }

    public Long getAuthorId() {
        return authorId;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;

    }

    public void setDate(String date) {
        this.date = date;
    }
}
