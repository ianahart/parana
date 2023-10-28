package com.hart.backend.parana.favorite.response;

public class DeleteFavoriteResponse {

    private String message;

    public DeleteFavoriteResponse() {

    }

    public DeleteFavoriteResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
