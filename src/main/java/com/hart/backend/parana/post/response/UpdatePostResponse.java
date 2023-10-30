package com.hart.backend.parana.post.response;

public class UpdatePostResponse {

    private String message;

    public UpdatePostResponse() {

    }

    public UpdatePostResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
