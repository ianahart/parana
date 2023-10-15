package com.hart.backend.parana.passwordreset.request;

public class ResetPasswordRequest {

    private String password;
    private String confirmPassword;
    private String passCode;
    private String token;

    public ResetPasswordRequest() {

    }

    public ResetPasswordRequest(
            String password,
            String confirmPassword,
            String passCode,
            String token) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.passCode = passCode;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPassCode() {
        return passCode;
    }

    public String getToken() {
        return token;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
