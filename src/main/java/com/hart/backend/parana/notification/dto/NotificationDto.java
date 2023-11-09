package com.hart.backend.parana.notification.dto;

import java.sql.Timestamp;

import com.amazonaws.services.dynamodbv2.model.Get;

public class NotificationDto {

    private Long id;
    private Long receiverId;
    private Long senderId;
    private String text;
    private String fullName;
    private String avatarUrl;
    private Timestamp createdAt;
    private String readableDate;

    public NotificationDto() {

    }

    public NotificationDto(
            Long id,
            Long receiverId,
            Long senderId,
            String text,
            String fullName,
            String avatarUrl,
            Timestamp createdAt) {
        this.id = id;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.text = text;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getReadableDate() {
        return readableDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setReadableDate(String readableDate) {
        this.readableDate = readableDate;
    }
}
