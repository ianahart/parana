package com.hart.backend.parana.connection.request;

public class UpdateConnectionRequest {

    private Long currentUserId;

    public UpdateConnectionRequest() {

    }

    public UpdateConnectionRequest(Long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }
}
