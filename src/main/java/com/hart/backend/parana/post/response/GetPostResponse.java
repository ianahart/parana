package com.hart.backend.parana.post.response;

import com.hart.backend.parana.post.dto.PostDto;
import com.hart.backend.parana.post.dto.PostPaginationDto;

public class GetPostResponse {

    private String message;
    private PostPaginationDto<PostDto> data;

    public GetPostResponse() {

    }

    public GetPostResponse(String message, PostPaginationDto<PostDto> data) {

        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public PostPaginationDto<PostDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(PostPaginationDto<PostDto> data) {
        this.data = data;
    }
}
