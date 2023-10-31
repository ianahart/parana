package com.hart.backend.parana.postlike.response;

public class DeletePostLikeResponse {

    private String message;

    public DeletePostLikeResponse() {
    }

    public DeletePostLikeResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
