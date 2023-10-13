package com.hart.backend.parana.user.response;

import com.hart.backend.parana.user.dto.UserDto;

public class SyncUserResponse {

    private UserDto data;
    private String message;

    public SyncUserResponse() {

    }

    public SyncUserResponse(String message, UserDto data) {
        this.message = message;
        this.data = data;
    }

    public UserDto getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setData(UserDto data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
