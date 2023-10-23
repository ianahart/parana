package com.hart.backend.parana.connection.response;

import com.hart.backend.parana.connection.dto.ConnectionPaginationDto;
import com.hart.backend.parana.connection.dto.ConnectionRequestDto;

public class GetConnectionRequestResponse {

    private String message;
    private ConnectionPaginationDto<ConnectionRequestDto> data;

    public GetConnectionRequestResponse() {

    }

    public GetConnectionRequestResponse(String message, ConnectionPaginationDto<ConnectionRequestDto> data) {
        this.message = message;
        this.data = data;
    }

    public ConnectionPaginationDto<ConnectionRequestDto> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setData(ConnectionPaginationDto<ConnectionRequestDto> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
