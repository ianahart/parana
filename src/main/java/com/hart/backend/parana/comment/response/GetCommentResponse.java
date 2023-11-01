package com.hart.backend.parana.comment.response;

import com.hart.backend.parana.comment.dto.CommentDto;
import com.hart.backend.parana.comment.dto.CommentPaginationDto;

public class GetCommentResponse {

    private String message;
    private CommentPaginationDto<CommentDto> data;

    public GetCommentResponse() {

    }

    public GetCommentResponse(String message, CommentPaginationDto<CommentDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public CommentPaginationDto<CommentDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(CommentPaginationDto<CommentDto> data) {
        this.data = data;
    }
}
