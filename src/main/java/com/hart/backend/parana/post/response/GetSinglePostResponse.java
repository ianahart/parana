package com.hart.backend.parana.post.response;

import com.hart.backend.parana.post.dto.EditPostDto;

public class GetSinglePostResponse {

    private String message;
    private EditPostDto data;

    public GetSinglePostResponse() {

    }

    public GetSinglePostResponse(String message, EditPostDto data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public EditPostDto getData() {
        return data;
    }

    public void setData(EditPostDto data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
