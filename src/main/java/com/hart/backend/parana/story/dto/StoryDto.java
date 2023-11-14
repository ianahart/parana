package com.hart.backend.parana.story.dto;

import java.sql.Timestamp;

public class StoryDto {

    private Long id;
    private Long userId;
    private Long expiresIn;
    private String avatarUrl;
    private String fullName;
    private String photoUrl;
    private String text;
    private String fontSize;
    private String duration;
    private String color;
    private String background;
    private String alignment;
    private Timestamp createdAt;

    public StoryDto() {

    }

    public StoryDto(
            Long id,
            Long userId,
            Long expiresIn,
            String avatarUrl,
            String fullName,
            String photoUrl,
            String text,
            String fontSize,
            String duration,
            String color,
            String background,
            String alignment,
            Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.expiresIn = expiresIn;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.photoUrl = photoUrl;
        this.text = text;
        this.fontSize = fontSize;
        this.duration = duration;
        this.color = color;
        this.background = background;
        this.alignment = alignment;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public String getDuration() {
        return duration;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getAlignment() {
        return alignment;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getBackground() {
        return background;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
