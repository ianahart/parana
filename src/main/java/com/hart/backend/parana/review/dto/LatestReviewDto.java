package com.hart.backend.parana.review.dto;

public class LatestReviewDto {

    private Long id;
    private Long userId;
    private String fullName;
    private String avatarUrl;
    private String review;
    private Byte rating;

    public LatestReviewDto() {

    }

    public LatestReviewDto(
            Long id,
            Long userId,
            String fullName,
            String avatarUrl,
            String review,
            Byte rating) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.review = review;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public Byte getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
