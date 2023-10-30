package com.hart.backend.parana.post.dto;

public class EditPostDto {
    private Long id;
    private String text;
    private String fileUrl;
    private String gif;

    public EditPostDto() {

    }

    public EditPostDto(
            Long id,
            String text,
            String fileUrl,
            String gif) {

        this.id = id;
        this.text = text;
        this.fileUrl = fileUrl;
        this.gif = gif;
    }

    public Long getId() {
        return id;
    }

    public String getGif() {
        return gif;
    }

    public String getText() {
        return text;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

}
