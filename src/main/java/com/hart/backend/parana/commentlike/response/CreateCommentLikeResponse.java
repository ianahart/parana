package com.hart.backend.parana.commentlike.response;

import com.hart.backend.parana.commentlike.dto.CreateCommentLikeDto;

public class CreateCommentLikeResponse {

    private String message;

    private CreateCommentLikeDto data;

    public CreateCommentLikeResponse() {

    }

    public CreateCommentLikeResponse(String message, CreateCommentLikeDto data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public CreateCommentLikeDto getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(CreateCommentLikeDto data) {
        this.data = data;
    }
}
