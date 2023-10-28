package com.hart.backend.parana.favorite.response;

import com.hart.backend.parana.favorite.dto.FavoriteDto;

public class GetFavoriteResponse {

    private String message;

    private FavoriteDto data;

    public GetFavoriteResponse() {

    }

    public GetFavoriteResponse(String message, FavoriteDto data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public FavoriteDto getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
