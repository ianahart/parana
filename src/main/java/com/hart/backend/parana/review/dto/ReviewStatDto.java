package com.hart.backend.parana.review.dto;

import java.util.List;

public class ReviewStatDto {

    private List<TopReviewDto> topReviews;
    private List<LatestReviewDto> latestReviews;
    private Long fiveStarReviews;

    public ReviewStatDto() {

    }

    public ReviewStatDto(List<TopReviewDto> topReviews, List<LatestReviewDto> latestReviews, Long fiveStarReviews) {
        this.topReviews = topReviews;
        this.latestReviews = latestReviews;
        this.fiveStarReviews = fiveStarReviews;
    }

    public List<LatestReviewDto> getLatestReviews() {
        return latestReviews;
    }

    public Long getFiveStarReviews() {
        return fiveStarReviews;
    }

    public List<TopReviewDto> getTopReviews() {
        return topReviews;
    }

    public void setTopReviews(List<TopReviewDto> topReviews) {
        this.topReviews = topReviews;
    }

    public void setLatestReviews(List<LatestReviewDto> latestReviews) {
        this.latestReviews = latestReviews;
    }

    public void setFiveStarReviews(Long fiveStarReviews) {
        this.fiveStarReviews = fiveStarReviews;
    }
}
