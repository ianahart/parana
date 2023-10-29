package com.hart.backend.parana.user.response;

import java.util.List;

import com.hart.backend.parana.user.dto.UserSuggestionDto;

public class GetUserSuggestionResponse {

    private String message;
    private List<UserSuggestionDto> data;

    public GetUserSuggestionResponse() {

    }

    public GetUserSuggestionResponse(String message, List<UserSuggestionDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserSuggestionDto> getData() {
        return data;
    }

    public void setData(List<UserSuggestionDto> data) {
        this.data = data;
    }
}
