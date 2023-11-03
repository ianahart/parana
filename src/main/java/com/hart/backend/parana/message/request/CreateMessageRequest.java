package com.hart.backend.parana.message.request;

public class CreateMessageRequest {

    private Long senderId;

    private Long receiverId;

    private String text;

    public CreateMessageRequest() {

    }

    public CreateMessageRequest(Long senderId, Long receiverId, String text) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

}
