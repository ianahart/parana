package com.hart.backend.parana.privacy.dto;

import java.sql.Timestamp;

public class PrivacyDto {

    private Long id;
    private Long blockedUserId;
    private String avatarUrl;
    private String fullName;
    private Boolean messages;
    private Boolean posts;
    private Boolean comments;
    private Timestamp updatedAt;
    private String readableDate;

    public PrivacyDto() {
    }

    public PrivacyDto(
            Long id,
            Long blockedUserId,
            String avatarUrl,
            String fullName,
            Boolean messages,
            Boolean posts,
            Boolean comments,
            Timestamp updatedAt) {
        this.id = id;
        this.blockedUserId = blockedUserId;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.messages = messages;
        this.posts = posts;
        this.comments = comments;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Boolean getPosts() {
        return posts;
    }

    public Boolean getComments() {
        return comments;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getMessages() {
        return messages;
    }

    public Long getBlockedUserId() {
        return blockedUserId;
    }

    public String getReadableDate() {
        return readableDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPosts(Boolean posts) {
        this.posts = posts;
    }

    public void setComments(Boolean comments) {
        this.comments = comments;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMessages(Boolean messages) {
        this.messages = messages;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setReadableDate(String readableDate) {
        this.readableDate = readableDate;
    }

    public void setBlockedUserId(Long blockedUserId) {
        this.blockedUserId = blockedUserId;
    }
}
