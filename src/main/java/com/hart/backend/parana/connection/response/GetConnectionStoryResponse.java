package com.hart.backend.parana.connection.response;

import com.hart.backend.parana.connection.dto.ConnectionPaginationDto;
import com.hart.backend.parana.connection.dto.ConnectionStoryDto;

public class GetConnectionStoryResponse {

    private String message;
    private ConnectionPaginationDto<ConnectionStoryDto> data;

    public GetConnectionStoryResponse() {

    }

    public GetConnectionStoryResponse(String message, ConnectionPaginationDto<ConnectionStoryDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public ConnectionPaginationDto<ConnectionStoryDto> getData() {
        return data;
    }

    public void setData(ConnectionPaginationDto<ConnectionStoryDto> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
