package com.hart.backend.parana.review.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class CreateReviewRequest {
    @Min(value = 1, message = "Rating should not be less than 1")
    @Max(value = 5, message = "Rating should not be greater than 5")
    private Byte rating;

    @Size(min = 1, max = 400, message = "Review must be between 1 and 400 characters")
    private String review;

    private Long userId;

    private Long teacherId;

    public CreateReviewRequest() {

    }

    public CreateReviewRequest(
            Byte rating,
            String review,
            Long userId,
            Long teacherId) {
        this.rating = rating;
        this.review = review;
        this.userId = userId;
        this.teacherId = teacherId;
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

    public Long getTeacherId() {
        return teacherId;
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

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
