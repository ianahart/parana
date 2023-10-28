package com.hart.backend.parana.favorite.dto;

public class FavoriteDto {
    private Long id;
    private Boolean isFavorited;

    public FavoriteDto() {

    }

    public FavoriteDto(Long id, Boolean isFavorited) {
        this.id = id;
        this.isFavorited = isFavorited;
    }

    public Long getId() {
        return id;
    }

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

}
