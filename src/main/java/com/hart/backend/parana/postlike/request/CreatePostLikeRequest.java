package com.hart.backend.parana.postlike.request;

public class CreatePostLikeRequest {

    private Long userId;
    private Long postId;

    public CreatePostLikeRequest() {

    }

    public CreatePostLikeRequest(Long userId, Long postId) {
         this.userId = userId;
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
