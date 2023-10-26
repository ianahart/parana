package com.hart.backend.parana.review.dto;

public class PartialReviewDto {

    private String review;
    private Byte rating;

    public PartialReviewDto() {

    }

    public PartialReviewDto(String review, Byte rating) {
        this.review = review;
        this.rating = rating;
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
}
