package com.hart.backend.parana.user.response;

import com.hart.backend.parana.user.dto.FullUserDto;

public class SyncUserResponse {

    private FullUserDto data;
    private String message;

    public SyncUserResponse() {

    }

    public SyncUserResponse(String message, FullUserDto data) {
        this.message = message;
        this.data = data;
    }

    public FullUserDto getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setData(FullUserDto data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
