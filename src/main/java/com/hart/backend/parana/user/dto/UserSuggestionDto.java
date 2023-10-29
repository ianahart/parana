package com.hart.backend.parana.user.dto;

import java.util.List;

public class UserSuggestionDto {

    private Long teacherId;
    private Long profileId;
    private String terrain;
    private String fullName;
    private String avatarUrl;
    private List<String> terrainInCommon;

    public UserSuggestionDto() {

    }

    public UserSuggestionDto(
            Long teacherId,
            Long profileId,
            String terrain,
            String fullName,
            String avatarUrl) {
        this.teacherId = teacherId;
        this.profileId = profileId;
        this.terrain = terrain;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
    }

    public String getTerrain() {
        return terrain;
    }

    public List<String> getTerrainInCommon() {
        return terrainInCommon;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Long getProfileId() {
        return profileId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setTerrainInCommon(List<String> terrainInCommon) {
        this.terrainInCommon = terrainInCommon;
    }
}
