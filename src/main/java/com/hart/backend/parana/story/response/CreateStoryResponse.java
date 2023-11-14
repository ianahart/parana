package com.hart.backend.parana.story.response;

public class CreateStoryResponse {

    private String message;

    public CreateStoryResponse() {

    }

    public CreateStoryResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
