package com.hart.backend.parana.setting.request;

public class UpdateSettingRequest {

    private Boolean rememberMe;
    private Boolean blockMessages;
    private Boolean blockComments;
    private Boolean blockPosts;

    public UpdateSettingRequest() {

    }

    public UpdateSettingRequest(
            Boolean rememberMe,
            Boolean blockMessages,
            Boolean blockComments,
            Boolean blockPosts) {

        this.rememberMe = rememberMe;
        this.blockMessages = blockMessages;
        this.blockComments = blockComments;
        this.blockPosts = blockPosts;
    }

    public Boolean getBlockPosts() {
        return blockPosts;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public Boolean getBlockMessages() {
        return blockMessages;
    }

    public Boolean getBlockComments() {
        return blockComments;
    }

    public void setBlockPosts(Boolean blockPosts) {
        this.blockPosts = blockPosts;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void setBlockComments(Boolean blockComments) {
        this.blockComments = blockComments;
    }

    public void setBlockMessages(Boolean blockMessages) {
        this.blockMessages = blockMessages;
    }
}
