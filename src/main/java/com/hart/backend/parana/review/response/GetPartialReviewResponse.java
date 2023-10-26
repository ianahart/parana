package com.hart.backend.parana.review.response;

import com.hart.backend.parana.review.dto.PartialReviewDto;

public class GetPartialReviewResponse {

    private String message;
    private PartialReviewDto data;

    public GetPartialReviewResponse() {

    }

    public GetPartialReviewResponse(String message, PartialReviewDto data) {
        this.message = message;
        this.data = data;
    }

    public PartialReviewDto getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setData(PartialReviewDto data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
