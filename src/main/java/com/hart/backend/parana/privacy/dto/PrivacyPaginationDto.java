package com.hart.backend.parana.privacy.dto;

import java.util.List;

public class PrivacyPaginationDto<T> {
    private List<T> privacies;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;

    public PrivacyPaginationDto() {

    }

    public PrivacyPaginationDto(List<T> privacies,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements) {
        this.privacies = privacies;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getDirection() {
        return direction;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getPrivacies() {
        return privacies;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setPrivacies(List<T> privacies) {
        this.privacies = privacies;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

}
