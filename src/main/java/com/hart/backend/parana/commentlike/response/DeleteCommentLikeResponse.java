package com.hart.backend.parana.commentlike.response;

public class DeleteCommentLikeResponse {

    private String message;

    public DeleteCommentLikeResponse() {

    }

    public DeleteCommentLikeResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
