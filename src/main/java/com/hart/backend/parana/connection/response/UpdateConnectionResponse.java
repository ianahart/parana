package com.hart.backend.parana.connection.response;

public class UpdateConnectionResponse {

    private String message;

    public UpdateConnectionResponse() {

    }

    public UpdateConnectionResponse(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
