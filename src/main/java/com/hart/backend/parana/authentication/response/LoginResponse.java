package com.hart.backend.parana.authentication.response;

import com.hart.backend.parana.user.dto.UserDto;

public class LoginResponse {

    private UserDto user;
    private String token;
    private String refreshToken;

    public LoginResponse() {

    }

    public LoginResponse(UserDto user, String token, String refreshToken) {
        this.user = user;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public UserDto getUser() {
        return user;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
