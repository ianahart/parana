package com.hart.backend.parana.post.response;

public class GetPostFeedResponse {

    private String message;

    public GetPostFeedResponse() {

    }

    public GetPostFeedResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
