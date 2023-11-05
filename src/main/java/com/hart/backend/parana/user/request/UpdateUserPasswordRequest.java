package com.hart.backend.parana.user.request;

public class UpdateUserPasswordRequest {

    private String oldPassword;
    private String password;
    private String confirmPassword;

    public UpdateUserPasswordRequest() {

    }

    public UpdateUserPasswordRequest(String oldPassword, String password, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
