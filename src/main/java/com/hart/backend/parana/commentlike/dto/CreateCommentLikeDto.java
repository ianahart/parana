package com.hart.backend.parana.commentlike.dto;

public class CreateCommentLikeDto {

    private Boolean currentUserHasLikedComment;

    public CreateCommentLikeDto() {

    }

    public CreateCommentLikeDto(Boolean currentUserHasLikedComment) {
        this.currentUserHasLikedComment = currentUserHasLikedComment;
    }

    public Boolean getCurrentUserHasLikedComment() {
        return currentUserHasLikedComment;
    }

    public void setCurrentUserHasLikedComment(Boolean currentUserHasLikedComment) {
        this.currentUserHasLikedComment = currentUserHasLikedComment;
    }
}
