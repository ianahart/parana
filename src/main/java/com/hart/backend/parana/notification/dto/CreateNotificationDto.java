package com.hart.backend.parana.notification.dto;

import com.hart.backend.parana.notification.Notification;

public class CreateNotificationDto {

    private Notification receiverNotification;
    private Notification senderNotification;

    public CreateNotificationDto() {

    }

    public CreateNotificationDto(Notification receiverNotification, Notification senderNotification) {
        this.receiverNotification = receiverNotification;
        this.senderNotification = senderNotification;
    }

    public Notification getSenderNotification() {
        return senderNotification;
    }

    public Notification getReceiverNotification() {
        return receiverNotification;
    }

    public void setSenderNotification(Notification senderNotification) {
        this.senderNotification = senderNotification;
    }

    public void setReceiverNotification(Notification receiverNotification) {
        this.receiverNotification = receiverNotification;
    }
}
