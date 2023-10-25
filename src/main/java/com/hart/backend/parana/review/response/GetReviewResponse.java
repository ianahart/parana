package com.hart.backend.parana.review.response;

import com.hart.backend.parana.review.dto.ReviewDto;
import com.hart.backend.parana.review.dto.ReviewPaginationDto;

public class GetReviewResponse {

    private String message;
    private ReviewPaginationDto<ReviewDto> data;

    public GetReviewResponse() {

    }

    public GetReviewResponse(String message, ReviewPaginationDto<ReviewDto> data) {
        this.message = message;
        this.data = data;
    }

    public ReviewPaginationDto<ReviewDto> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setData(ReviewPaginationDto<ReviewDto> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
