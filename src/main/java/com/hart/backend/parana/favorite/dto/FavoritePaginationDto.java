package com.hart.backend.parana.favorite.dto;

import java.util.List;

public class FavoritePaginationDto<T> {
    private List<T> favorites;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;

    public FavoritePaginationDto() {

    }

    public FavoritePaginationDto(
            List<T> favorites,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements) {

        this.favorites = favorites;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public List<T> getFavorites() {
        return favorites;
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

    public void setFavorites(List<T> favorites) {
        this.favorites = favorites;
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
