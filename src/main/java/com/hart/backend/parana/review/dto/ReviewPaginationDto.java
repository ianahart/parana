package com.hart.backend.parana.review.dto;

import java.util.List;

public class ReviewPaginationDto<T> {
    private List<T> reviews;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;
    private Byte avgRating;

    public ReviewPaginationDto(
            List<T> reviews,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements,
            Byte avgRating) {
        this.reviews = reviews;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
        this.avgRating = avgRating;
    }

    public int getPage() {
        return page;
    }

    public Byte getAvgRating() {
        return avgRating;
    }

    public List<T> getReviews() {
        return reviews;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public String getDirection() {
        return direction;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setReviews(List<T> reviews) {
        this.reviews = reviews;
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

    public void setAvgRating(Byte avgRating) {
        this.avgRating = avgRating;
    }
}
