package com.hart.backend.parana.message.response;

public class CreateMessageResponse {

    private String message;

    public CreateMessageResponse() {

    }

    public CreateMessageResponse(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
