package com.hart.backend.parana.notification.response;

import com.hart.backend.parana.notification.dto.NotificationDto;
import com.hart.backend.parana.notification.dto.NotificationPaginationDto;

public class GetNotificationResponse {

    private String message;

    private NotificationPaginationDto<NotificationDto> data;

    public GetNotificationResponse() {

    }

    public GetNotificationResponse(String message, NotificationPaginationDto<NotificationDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public NotificationPaginationDto<NotificationDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(NotificationPaginationDto<NotificationDto> data) {
        this.data = data;
    }
}
