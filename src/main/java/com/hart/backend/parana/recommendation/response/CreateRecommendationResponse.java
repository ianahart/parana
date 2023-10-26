package com.hart.backend.parana.recommendation.response;

public class CreateRecommendationResponse {

    private String message;

    public CreateRecommendationResponse() {

    }

    public CreateRecommendationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
