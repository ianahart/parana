package com.hart.backend.parana.replycomment.response;

import com.hart.backend.parana.replycomment.dto.ReplyCommentDto;
import com.hart.backend.parana.replycomment.dto.ReplyCommentPaginationDto;

public class GetReplyCommentResponse {

    private String message;
    private ReplyCommentPaginationDto<ReplyCommentDto> data;

    public GetReplyCommentResponse() {

    }

    public GetReplyCommentResponse(String message, ReplyCommentPaginationDto<ReplyCommentDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public ReplyCommentPaginationDto<ReplyCommentDto> getData() {
        return data;
    }

    public void setData(ReplyCommentPaginationDto<ReplyCommentDto> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
