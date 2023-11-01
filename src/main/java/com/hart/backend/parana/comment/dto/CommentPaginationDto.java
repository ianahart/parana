package com.hart.backend.parana.comment.dto;

import java.util.List;

public class CommentPaginationDto<T> {
    private List<T> comments;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;

    public CommentPaginationDto() {

    }

    public CommentPaginationDto(
            List<T> comments,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements) {

        this.comments = comments;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public List<T> getComments() {
        return comments;
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

    public void setComments(List<T> comments) {
        this.comments = comments;
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
