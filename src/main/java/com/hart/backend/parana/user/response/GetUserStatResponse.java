package com.hart.backend.parana.user.response;

public class GetUserStatResponse {

    private String message;
    private Long data;

    public GetUserStatResponse() {

    }

    public GetUserStatResponse(String message, Long data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
