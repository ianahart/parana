package com.hart.backend.parana.setting.dto;

import java.sql.Timestamp;

public class SettingDto {
    private Long id;
    private Long userId;
    private Timestamp createdAt;
    private Boolean rememberMe;
    private Boolean blockMessages;
    private Boolean blockComments;
    private Boolean blockPosts;
    private String ipAddress;
    Timestamp passwordUpdatedOn;
    Timestamp emailUpdatedOn;
    Boolean notifications;
    String updatedFormattedDate;
    String emailUpdatedFormattedDate;

    public SettingDto() {

    }

    public SettingDto(
            Long id,
            Long userId,
            Timestamp createdAt,
            Boolean rememberMe,
            Boolean blockMessages,
            Boolean blockComments,
            Boolean blockPosts,
            String ipAddress,
            Timestamp passwordUpdatedOn,
            Timestamp emailUpdatedOn,
            Boolean notifications) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.rememberMe = rememberMe;
        this.blockMessages = blockMessages;
        this.blockComments = blockComments;
        this.blockPosts = blockPosts;
        this.ipAddress = ipAddress;
        this.passwordUpdatedOn = passwordUpdatedOn;
        this.emailUpdatedOn = emailUpdatedOn;
        this.notifications = notifications;
    }

    public Long getId() {
        return id;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    public Timestamp getEmailUpdatedOn() {
        return emailUpdatedOn;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmailUpdatedFormattedDate() {
        return emailUpdatedFormattedDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Boolean getBlockPosts() {
        return blockPosts;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public Boolean getBlockComments() {
        return blockComments;
    }

    public Timestamp getPasswordUpdatedOn() {
        return passwordUpdatedOn;
    }

    public Boolean getBlockMessages() {
        return blockMessages;
    }

    public String getUpdatedFormattedDate() {
        return updatedFormattedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmailUpdatedOn(Timestamp emailUpdatedOn) {
        this.emailUpdatedOn = emailUpdatedOn;
    }

    public void setEmailUpdatedFormattedDate(String emailUpdatedFormattedDate) {
        this.emailUpdatedFormattedDate = emailUpdatedFormattedDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setBlockPosts(Boolean blockPosts) {
        this.blockPosts = blockPosts;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void setBlockComments(Boolean blockComments) {
        this.blockComments = blockComments;
    }

    public void setBlockMessages(Boolean blockMessages) {
        this.blockMessages = blockMessages;
    }

    public void setPasswordUpdatedOn(Timestamp passwordUpdatedOn) {
        this.passwordUpdatedOn = passwordUpdatedOn;
    }

    public void setUpdatedFormattedDate(String updatedFormattedDate) {
        this.updatedFormattedDate = updatedFormattedDate;
    }
}
