package com.hart.backend.parana.connection.response;

import com.hart.backend.parana.connection.dto.ConnectionDto;
import com.hart.backend.parana.connection.dto.ConnectionPaginationDto;

public class GetConnectionRequestResponse {

    private String message;
    private ConnectionPaginationDto<ConnectionDto> data;

    public GetConnectionRequestResponse() {

    }

    public GetConnectionRequestResponse(String message, ConnectionPaginationDto<ConnectionDto> data) {
        this.message = message;
        this.data = data;
    }

    public ConnectionPaginationDto<ConnectionDto> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setData(ConnectionPaginationDto<ConnectionDto> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
