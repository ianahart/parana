package com.hart.backend.parana.comment.dto;

import java.sql.Timestamp;

public class CommentDto {

    private Long id;
    private Long userId;
    private Long postId;
    private String fullName;
    private String avatarUrl;
    private String text;
    private Timestamp createdAt;
    private Long likesCount;
    private Boolean currentUserHasLikedComment;

    public CommentDto() {

    }

    public CommentDto(
            Long id,
            Long userId,
            Long postId,
            String fullName,
            String avatarUrl,
            String text,
            Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Long getPostId() {
        return postId;
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

    public Long getLikesCount() {
        return likesCount;
    }

    public Boolean getCurrentUserHasLikedComment() {
        return currentUserHasLikedComment;
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

    public void setPostId(Long postId) {
        this.postId = postId;
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

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public void setCurrentUserHasLikedComment(Boolean currentUserHasLikedComment) {
        this.currentUserHasLikedComment = currentUserHasLikedComment;
    }
}
