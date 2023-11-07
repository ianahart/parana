package com.hart.backend.parana.user.dto;

public class MinimalUserDto {

    private Long userId;
    private String fullName;
    private String avatarUrl;

    public MinimalUserDto() {

    }

    public MinimalUserDto(Long userId, String fullName, String avatarUrl) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
