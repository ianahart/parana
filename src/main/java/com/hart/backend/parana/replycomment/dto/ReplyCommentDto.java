package com.hart.backend.parana.replycomment.dto;

import java.sql.Timestamp;

public class ReplyCommentDto {
    private Long id;
    private Long userId;
    private String avatarUrl;
    private String text;
    private Timestamp createdAt;
    private String fullName;

    public ReplyCommentDto() {

    }

    public ReplyCommentDto(
            Long id,
            Long userId,
            String avatarUrl,
            String text,
            Timestamp createdAt,
            String fullName) {

        this.id = id;
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.text = text;
        this.createdAt = createdAt;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
