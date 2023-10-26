package com.hart.backend.parana.recommendation.response;

public class DeleteRecommendationResponse {

    private String message;

    public DeleteRecommendationResponse() {

    }

    public DeleteRecommendationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
