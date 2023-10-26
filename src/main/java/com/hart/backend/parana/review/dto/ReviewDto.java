package com.hart.backend.parana.review.dto;

public class ReviewDto {

    private Byte rating;
    private String review;
    private Long id;
    private Long userId;
    private String avatarUrl;
    private String firstName;
    private Boolean isEdited;
    private String fullName;

    public ReviewDto() {

    }

    public ReviewDto(
            Byte rating,
            String review,
            Long id,
            Long userId,
            String avatarUrl,
            String firstName,
            Boolean isEdited,
            String fullName) {
        this.rating = rating;
        this.review = review;
        this.id = id;
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.firstName = firstName;
        this.isEdited = isEdited;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public Byte getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
