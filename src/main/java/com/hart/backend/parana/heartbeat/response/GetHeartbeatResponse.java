package com.hart.backend.parana.heartbeat.response;

public class GetHeartbeatResponse {

    private String message;

    public GetHeartbeatResponse() {

    }

    public GetHeartbeatResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
