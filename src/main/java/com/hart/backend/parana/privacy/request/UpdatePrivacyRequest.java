package com.hart.backend.parana.privacy.request;

public class UpdatePrivacyRequest {

    private String block;
    private Boolean isChecked;

    public UpdatePrivacyRequest() {

    }

    public UpdatePrivacyRequest(String block, Boolean isChecked) {
        this.block = block;
        this.isChecked = isChecked;
    }

    public String getBlock() {
        return block;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }
}
