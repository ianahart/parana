package com.hart.backend.parana.connection.dto;

public class MinimalConnectionDto {

    private Long userId;

    public MinimalConnectionDto() {

    }

    public MinimalConnectionDto(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
