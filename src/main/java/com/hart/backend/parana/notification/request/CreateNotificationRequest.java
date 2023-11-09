package com.hart.backend.parana.notification.request;

public class CreateNotificationRequest {

    private Long receiverId;

    private Long senderId;

    private String type;

    public CreateNotificationRequest() {

    }

    public CreateNotificationRequest(Long receiverId, Long senderId, String type) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}
