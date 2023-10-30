package com.hart.backend.parana.post.response;

public class CreatePostResponse {

    private String message;

    public CreatePostResponse() {

    }

    public CreatePostResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
