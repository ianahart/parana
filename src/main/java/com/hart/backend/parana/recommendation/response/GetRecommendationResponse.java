package com.hart.backend.parana.recommendation.response;

import com.hart.backend.parana.recommendation.dto.RecommendationDto;
import com.hart.backend.parana.recommendation.dto.RecommendationPaginationDto;

public class GetRecommendationResponse {

    private String message;
    private RecommendationPaginationDto<RecommendationDto> data;

    public GetRecommendationResponse() {

    }

    public GetRecommendationResponse(String message, RecommendationPaginationDto<RecommendationDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public RecommendationPaginationDto<RecommendationDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(RecommendationPaginationDto<RecommendationDto> data) {
        this.data = data;
    }
}
