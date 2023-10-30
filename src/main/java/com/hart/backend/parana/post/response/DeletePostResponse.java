package com.hart.backend.parana.post.response;

public class DeletePostResponse {

    private String message;

    public DeletePostResponse() {

    }

    public DeletePostResponse(String message) {
          this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
