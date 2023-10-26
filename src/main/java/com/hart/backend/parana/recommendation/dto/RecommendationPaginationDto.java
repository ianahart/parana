package com.hart.backend.parana.recommendation.dto;

import java.util.List;

public class RecommendationPaginationDto<T> {
    private List<T> recommendations;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;

    public RecommendationPaginationDto() {

    }

    public RecommendationPaginationDto(
            List<T> recommendations,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements) {

        this.recommendations = recommendations;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public List<T> getRecommendations() {
        return recommendations;
    }

    public String getDirection() {
        return direction;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setRecommendations(List<T> recommendations) {
        this.recommendations = recommendations;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
