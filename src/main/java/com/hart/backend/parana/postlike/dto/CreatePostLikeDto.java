package com.hart.backend.parana.postlike.dto;

public class CreatePostLikeDto {

    private Boolean currentUserHasLikedPost;

    public CreatePostLikeDto() {

    }

    public CreatePostLikeDto(Boolean currentUserHasLikedPost) {
        this.currentUserHasLikedPost = currentUserHasLikedPost;
    }

    public Boolean getCurrentUserHasLikedPost() {
        return currentUserHasLikedPost;
    }

    public void setCurrentUserHasLikedPost(Boolean currentUserHasLikedPost) {
        this.currentUserHasLikedPost = currentUserHasLikedPost;
    }
}
