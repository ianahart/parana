package com.hart.backend.parana.user.dto;

import java.util.List;

public class UserPaginationDto<T> {
    private List<T> users;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;

    public UserPaginationDto() {

    }

    public UserPaginationDto(
            List<T> users,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements) {

        this.users = users;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public List<T> getUsers() {
        return users;
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

    public void setUsers(List<T> users) {
        this.users = users;
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
