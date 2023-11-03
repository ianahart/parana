package com.hart.backend.parana.message.dto;

import java.sql.Timestamp;

public class MessageDto {

    private Long id;
    private Timestamp createdAt;
    private Long senderUserId;
    private Long receiverUserId;
    private String senderFullName;
    private String receiverFullName;
    private String receiverAvatarUrl;
    private String senderAvatarUrl;
    private String text;
    private String readableDate;

    public MessageDto() {

    }

    public MessageDto(
            Long id,
            Timestamp createdAt,
            Long senderUserId,
            Long receiverUserId,
            String senderFullName,
            String receiverFullName,
            String receiverAvatarUrl,
            String senderAvatarUrl,
            String text) {
        this.id = id;
        this.createdAt = createdAt;
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.senderFullName = senderFullName;
        this.receiverFullName = receiverFullName;
        this.receiverAvatarUrl = receiverAvatarUrl;
        this.senderAvatarUrl = senderAvatarUrl;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public Long getReceiverUserId() {
        return receiverUserId;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public String getSenderAvatarUrl() {
        return senderAvatarUrl;
    }

    public String getReceiverFullName() {
        return receiverFullName;
    }

    public String getReceiverAvatarUrl() {
        return receiverAvatarUrl;
    }

    public String getReadableDate() {
        return readableDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setReadableDate(String readableDate) {
        this.readableDate = readableDate;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public void setReceiverUserId(Long receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    public void setSenderAvatarUrl(String senderAvatarUrl) {
        this.senderAvatarUrl = senderAvatarUrl;
    }

    public void setReceiverFullName(String receiverFullName) {
        this.receiverFullName = receiverFullName;
    }

    public void setReceiverAvatarUrl(String receiverAvatarUrl) {
        this.receiverAvatarUrl = receiverAvatarUrl;
    }

    public void setText(String text) {
        this.text = text;
    }
}
