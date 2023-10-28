package com.hart.backend.parana.favorite.dto;

import java.sql.Timestamp;

public class FullFavoriteDto {

    private Long id;
    private Long teacherId;
    private Long profileId;
    private String avatarUrl;
    private String fullName;
    private Timestamp createdAt;
    private String readableDate;
    private Boolean isConnected;

    public FullFavoriteDto() {

    }

    public FullFavoriteDto(
            Long id,
            Long teacherId,
            Long profileId,
            String avatarUrl,
            String fullName,
            Timestamp createdAt) {
        this.id = id;
        this.teacherId = teacherId;
        this.profileId = profileId;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public Boolean getIsConnected() {
        return isConnected;
    }

    public String getReadableDate() {
        return readableDate;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void setReadableDate(String readableDate) {
        this.readableDate = readableDate;
    }

}
