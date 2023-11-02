package com.hart.backend.parana.commentlike.request;

public class CreateCommentLikeRequest {

    private Long userId;

    public CreateCommentLikeRequest() {

    }

    public CreateCommentLikeRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
