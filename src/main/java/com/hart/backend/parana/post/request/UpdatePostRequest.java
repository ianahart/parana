package com.hart.backend.parana.post.request;

import org.springframework.web.multipart.MultipartFile;

public class UpdatePostRequest {
    private String text;
    private MultipartFile file;
    private String gif;

    public UpdatePostRequest() {

    }

    public UpdatePostRequest(String text, MultipartFile file, String gif) {
        this.text = text;
        this.file = file;
        this.gif = gif;
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

    public void setGif(String gif) {
        this.gif = gif;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setText(String text) {
        this.text = text;
    }
}
