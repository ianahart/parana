package com.hart.backend.parana.story.response;

public class GetStoryResponse {

    private String message;

    public GetStoryResponse() {

    }

    public GetStoryResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
