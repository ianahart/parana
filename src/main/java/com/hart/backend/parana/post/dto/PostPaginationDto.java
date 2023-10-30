package com.hart.backend.parana.post.dto;

import java.util.List;

public class PostPaginationDto<T> {
    private List<T> posts;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;

    public PostPaginationDto() {

    }

    public PostPaginationDto(
            List<T> posts,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements) {
        this.posts = posts;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public List<T> getPosts() {
        return posts;
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

    public void setPosts(List<T> posts) {
        this.posts = posts;
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
