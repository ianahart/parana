package com.hart.backend.parana.user.response;

import com.hart.backend.parana.user.dto.MinimalUserDto;
import com.hart.backend.parana.user.dto.UserPaginationDto;

public class GetNonBlockedUserResponse {

    private String message;
    private UserPaginationDto<MinimalUserDto> data;

    public GetNonBlockedUserResponse() {

    }

    public GetNonBlockedUserResponse(String message, UserPaginationDto<MinimalUserDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public UserPaginationDto<MinimalUserDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(UserPaginationDto<MinimalUserDto> data) {
        this.data = data;
    }
}
