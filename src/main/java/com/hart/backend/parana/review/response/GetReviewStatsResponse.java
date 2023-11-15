package com.hart.backend.parana.review.response;

import com.hart.backend.parana.review.dto.ReviewStatDto;

public class GetReviewStatsResponse {

    private String message;
    private ReviewStatDto data;

    public GetReviewStatsResponse() {

    }

    public GetReviewStatsResponse(String message, ReviewStatDto data) {
        this.message = message;
        this.data = data;
    }

    public ReviewStatDto getData() {
        return data;
    }

    public void setData(ReviewStatDto data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
