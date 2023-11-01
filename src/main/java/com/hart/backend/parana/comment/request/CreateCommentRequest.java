package com.hart.backend.parana.comment.request;

import jakarta.validation.constraints.Size;

public class CreateCommentRequest {

    private Long userId;

    private Long postId;

    @Size(min = 1, max = 300, message = "Comment must be between 1 and 300 characters")
    private String text;

    private Long ownerId;

    public CreateCommentRequest() {

    }

    public CreateCommentRequest(Long userId, Long postId, String text, Long ownerId) {
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.ownerId = ownerId;
    }

    public String getText() {
        return text;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
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

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
