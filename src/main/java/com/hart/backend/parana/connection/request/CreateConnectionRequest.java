package com.hart.backend.parana.connection.request;

public class CreateConnectionRequest {

    private Long senderId;
    private Long receiverId;

    public CreateConnectionRequest() {

    }

    public CreateConnectionRequest(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}
