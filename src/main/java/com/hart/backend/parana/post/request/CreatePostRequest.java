package com.hart.backend.parana.post.request;

import org.springframework.web.multipart.MultipartFile;

public class CreatePostRequest {

    private Long ownerId;
    private Long authorId;
    private String text;
    private String gif;
    private MultipartFile file;

    public CreatePostRequest() {

    }

    public CreatePostRequest(Long ownerId, Long authorId, String text) {
        this.ownerId = ownerId;
        this.authorId = authorId;
        this.text = text;
    }

    public CreatePostRequest(Long ownerId, Long authorId, String text, String gif) {
        this.ownerId = ownerId;
        this.authorId = authorId;
        this.text = text;
        this.gif = gif;
    }

    public CreatePostRequest(Long ownerId, Long authorId, String text, MultipartFile file) {
        this.ownerId = ownerId;
        this.authorId = authorId;
        this.text = text;
        this.file = file;
    }

    public String getGif() {
        return gif;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getText() {
        return text;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

}
