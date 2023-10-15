package com.hart.backend.parana.profile.request;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePhotoRequest {

    private MultipartFile file;
    private String action;

    public ProfilePhotoRequest() {

    }

    public ProfilePhotoRequest(MultipartFile file, String action) {

        this.file = file;
        this.action = action;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
