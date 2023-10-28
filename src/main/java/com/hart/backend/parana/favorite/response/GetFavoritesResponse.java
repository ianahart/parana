package com.hart.backend.parana.favorite.response;

import com.hart.backend.parana.favorite.dto.FavoritePaginationDto;
import com.hart.backend.parana.favorite.dto.FullFavoriteDto;

public class GetFavoritesResponse {

    private String message;

    private FavoritePaginationDto<FullFavoriteDto> data;

    public GetFavoritesResponse() {

    }

    public GetFavoritesResponse(String message, FavoritePaginationDto<FullFavoriteDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public FavoritePaginationDto<FullFavoriteDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(FavoritePaginationDto<FullFavoriteDto> data) {
        this.data = data;
    }
}
