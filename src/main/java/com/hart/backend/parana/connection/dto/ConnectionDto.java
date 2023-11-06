package com.hart.backend.parana.connection.dto;

import java.sql.Timestamp;

public class ConnectionDto {

    private Boolean loggedIn;
    private String fullName;
    private String avatarUrl;
    private Timestamp createdAt;
    private Long userId;
    private Long id;
    private String readableDate;
    private Long profileId;

    public ConnectionDto() {

    }

    public ConnectionDto(Boolean loggedIn, String fullName, String avatarUrl, Timestamp createdAt,
            Long userId, Long id, Long profileId) {
        this.loggedIn = loggedIn;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.userId = userId;
        this.id = id;
        this.profileId = profileId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public Long getId() {
        return id;

    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getReadableDate() {
        return readableDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setReadableDate(String readableDate) {
        this.readableDate = readableDate;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
