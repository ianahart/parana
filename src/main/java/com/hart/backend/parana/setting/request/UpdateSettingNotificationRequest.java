package com.hart.backend.parana.setting.request;

public class UpdateSettingNotificationRequest {

    private Boolean notifications;

    public UpdateSettingNotificationRequest() {

    }

    public UpdateSettingNotificationRequest(Boolean notifications) {
        this.notifications = notifications;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }
}
