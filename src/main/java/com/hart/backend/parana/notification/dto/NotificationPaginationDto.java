package com.hart.backend.parana.notification.dto;

import java.util.List;

public class NotificationPaginationDto<T> {
    private List<T> notifications;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;

    public NotificationPaginationDto() {

    }

    public NotificationPaginationDto(
            List<T> notifications,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements) {

        this.notifications = notifications;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public List<T> getNotifications() {
        return notifications;
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

    public void setNotifications(List<T> notifications) {
        this.notifications = notifications;
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
