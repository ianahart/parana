package com.hart.backend.parana.setting.request;

public class UpdateRememberMeRequest {

    private Boolean rememberMe;

    public UpdateRememberMeRequest() {
    }

    public UpdateRememberMeRequest(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
