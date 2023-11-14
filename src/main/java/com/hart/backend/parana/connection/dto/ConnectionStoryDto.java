package com.hart.backend.parana.connection.dto;

import java.util.List;

import com.hart.backend.parana.story.dto.StoryDto;

public class ConnectionStoryDto {

    private List<StoryDto> stories;
    private Long userId;
    private String fullName;
    private String avatarUrl;

    public ConnectionStoryDto() {

    }

    public ConnectionStoryDto(
            List<StoryDto> stories,
            Long userId,
            String fullName,
            String avatarUrl) {
        this.stories = stories;
        this.userId = userId;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public List<StoryDto> getStories() {
        return stories;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setStories(List<StoryDto> stories) {
        this.stories = stories;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
