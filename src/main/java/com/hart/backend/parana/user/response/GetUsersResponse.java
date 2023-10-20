package com.hart.backend.parana.user.response;


public class GetUsersResponse<T> {

    private String message;
    private T data;

    public GetUsersResponse() {
    }

    public GetUsersResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
