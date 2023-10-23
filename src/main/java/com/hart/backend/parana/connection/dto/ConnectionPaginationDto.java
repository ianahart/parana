package com.hart.backend.parana.connection.dto;

import java.util.List;

public class ConnectionPaginationDto<T> {
    private List<T> connections;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;

    public ConnectionPaginationDto() {

    }

    public ConnectionPaginationDto(
            List<T> connections,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements) {

        this.connections = connections;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public List<T> getConnections() {
        return connections;
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

    public void setConnections(List<T> connections) {
        this.connections = connections;
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
