package com.hart.backend.parana.connection.response;

import com.hart.backend.parana.connection.dto.ConnectionStatusDto;

public class CreateConnectionResponse {

    private String message;
    private ConnectionStatusDto data;

    public CreateConnectionResponse() {

    }

    public CreateConnectionResponse(String message, ConnectionStatusDto data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public ConnectionStatusDto getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(ConnectionStatusDto data) {
        this.data = data;
    }
}
