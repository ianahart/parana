package com.hart.backend.parana.review.dto;

public class TopReviewDto {

    private Long userId;
    private String fullName;
    private String avatarUrl;
    private Double averageRating;
    private Long numReviews;

    public TopReviewDto() {

    }

    public TopReviewDto(
            Long userId,
            String fullName,
            String avatarUrl,
            Double averageRating,
            Long numReviews) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.averageRating = averageRating;
        this.numReviews = numReviews;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Long getNumReviews() {
        return numReviews;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setNumReviews(Long numReviews) {
        this.numReviews = numReviews;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
