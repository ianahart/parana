package com.hart.backend.parana.authentication.response;

import com.hart.backend.parana.user.dto.FullUserDto;

public class LoginResponse {

    private FullUserDto user;
    private String token;
    private String refreshToken;

    public LoginResponse() {

    }

    public LoginResponse(FullUserDto user, String token, String refreshToken) {
        this.user = user;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public FullUserDto getUser() {
        return user;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setUser(FullUserDto user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
