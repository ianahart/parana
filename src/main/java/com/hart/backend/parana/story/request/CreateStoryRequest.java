package com.hart.backend.parana.story.request;

import org.springframework.web.multipart.MultipartFile;

public class CreateStoryRequest {

    private Long userId;

    private MultipartFile file;

    private String duration;

    private String type;

    public CreateStoryRequest() {

    }

    public CreateStoryRequest(
            Long userId,
            MultipartFile file,
            String duration,
            String type) {

        this.userId = userId;
        this.file = file;
        this.duration = duration;
        this.type = type;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getType() {
        return type;
    }

    public Long getUserId() {
        return userId;
    }

    public String getDuration() {
        return duration;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setType(String type) {
        this.type = type;
    }
}
