package com.hart.backend.parana.postlike.response;

import com.hart.backend.parana.postlike.dto.CreatePostLikeDto;

public class CreatePostLikeResponse {

    private String message;
    private CreatePostLikeDto data;

    public CreatePostLikeResponse() {

    }

    public CreatePostLikeResponse(String message, CreatePostLikeDto data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public CreatePostLikeDto getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(CreatePostLikeDto data) {
        this.data = data;
    }
}
