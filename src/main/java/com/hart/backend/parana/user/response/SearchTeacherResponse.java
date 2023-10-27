package com.hart.backend.parana.user.response;

import com.hart.backend.parana.user.dto.SearchTeacherDto;
import com.hart.backend.parana.user.dto.UserPaginationDto;

public class SearchTeacherResponse {

    private String message;
    private UserPaginationDto<SearchTeacherDto> data;

    public SearchTeacherResponse() {

    }

    public SearchTeacherResponse(String message, UserPaginationDto<SearchTeacherDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public UserPaginationDto<SearchTeacherDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(UserPaginationDto<SearchTeacherDto> data) {
        this.data = data;
    }
}
