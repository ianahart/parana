package com.hart.backend.parana.post.dto;

import java.sql.Timestamp;

public class PostDto {

    private Long id;
    private Long ownerId;
    private String text;
    private String gif;
    private String fileUrl;
    private Timestamp createdAt;
    private Boolean isEdited;
    private String readableDate;
    private String authorFullName;
    private String authorAvatarUrl;
    private Long authorId;
    private Boolean currentUserHasLikedPost;
    private Long likesCount;

    public PostDto() {

    }

    public PostDto(
            Long id,
            Long ownerId,
            String text,
            String gif,
            String fileUrl,
            Timestamp createdAt,
            Boolean isEdited,
            String authorFullName,
            String authorAvatarUrl,
            Long authorId) {
        this.id = id;
        this.ownerId = ownerId;
        this.text = text;
        this.gif = gif;
        this.fileUrl = fileUrl;
        this.createdAt = createdAt;
        this.isEdited = isEdited;
        this.authorFullName = authorFullName;
        this.authorAvatarUrl = authorAvatarUrl;
        this.authorId = authorId;
    }

    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public Long getId() {
        return id;
    }

    public String getGif() {
        return gif;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getText() {
        return text;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public String getReadableDate() {
        return readableDate;
    }

    public Boolean getCurrentUserHasLikedPost() {
        return currentUserHasLikedPost;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setReadableDate(String readableDate) {
        this.readableDate = readableDate;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }

    public void setAuthorAvatarUrl(String authorAvatarUrl) {
        this.authorAvatarUrl = authorAvatarUrl;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setCurrentUserHasLikedPost(Boolean currentUserHasLikedPost) {
        this.currentUserHasLikedPost = currentUserHasLikedPost;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }
}
