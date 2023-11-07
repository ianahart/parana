package com.hart.backend.parana.privacy.dto;

public class BlockDto {

    private Boolean messages;

    private Boolean posts;

    private Boolean comments;

    public BlockDto() {

    }

    public BlockDto(Boolean messages, Boolean posts, Boolean comments) {
        this.messages = messages;
        this.posts = posts;
        this.comments = comments;
    }

    public Boolean getPosts() {
        return posts;
    }

    public Boolean getComments() {
        return comments;
    }

    public Boolean getMessages() {
        return messages;
    }

    public void setPosts(Boolean posts) {
        this.posts = posts;
    }

    public void setComments(Boolean comments) {
        this.comments = comments;
    }

    public void setMessages(Boolean messages) {
        this.messages = messages;
    }
}
