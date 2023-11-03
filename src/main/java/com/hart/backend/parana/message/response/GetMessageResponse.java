package com.hart.backend.parana.message.response;

import java.util.List;

import com.hart.backend.parana.message.dto.MessageDto;

public class GetMessageResponse {

    private String message;

    private List<MessageDto> data;

    public GetMessageResponse() {

    }

    public GetMessageResponse(String message, List<MessageDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public List<MessageDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<MessageDto> data) {
        this.data = data;
    }
}
