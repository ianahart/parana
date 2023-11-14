package com.hart.backend.parana.connection.dto;

public class ActiveConnectionDto {

    private Long userId;
    private Boolean loggedIn;

    public ActiveConnectionDto() {

    }

    public ActiveConnectionDto(Long userId, Boolean loggedIn) {
        this.userId = userId;
        this.loggedIn = loggedIn;
    }

    public Long getUserId() {
        return userId;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
