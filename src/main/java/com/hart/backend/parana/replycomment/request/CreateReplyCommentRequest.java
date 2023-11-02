package com.hart.backend.parana.replycomment.request;

import jakarta.validation.constraints.Size;

public class CreateReplyCommentRequest {

    private Long userId;

    private Long commentId;

    @Size(min = 1, max = 300, message = "Comment must be between 1 and 300 characters")
    private String text;

    private Long ownerId;

    public CreateReplyCommentRequest() {

    }

    public CreateReplyCommentRequest(
            Long userId,
            Long commentId,
            String text,
            Long ownerId) {
        this.userId = userId;
        this.commentId = commentId;
        this.text = text;
        this.ownerId = ownerId;
    }

    public String getText() {
        return text;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
