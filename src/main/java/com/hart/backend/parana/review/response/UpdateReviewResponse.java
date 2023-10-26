package com.hart.backend.parana.review.response;

public class UpdateReviewResponse {

    private String message;

    public UpdateReviewResponse() {

    }

    public UpdateReviewResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
