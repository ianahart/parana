package com.hart.backend.parana.recommendation.request;

import jakarta.validation.constraints.Size;

public class CreateRecommendationRequest {

    private Long authorId;

    private Long teacherId;

    @Size(min = 1, max = 400, message = "Recommendation must be between 1 and 400 charactesr")
    private String recommendation;

    @Size(max = 400, message = "Words cannot exceed 400 characters")
    private String words;

    public CreateRecommendationRequest() {

    }

    public CreateRecommendationRequest(
            Long authorId,
            Long teacherId,
            String recommendation,
            String words) {
        this.authorId = authorId;
        this.teacherId = teacherId;
        this.recommendation = recommendation;
        this.words = words;
    }

    public String getWords() {
        return words;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
