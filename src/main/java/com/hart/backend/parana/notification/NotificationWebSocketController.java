package com.hart.backend.parana.notification;

import com.hart.backend.parana.notification.dto.CreateNotificationDto;
import com.hart.backend.parana.notification.dto.NotificationDto;
import com.hart.backend.parana.notification.request.CreateNotificationRequest;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationWebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final NotificationService notificationService;

    private final UserService userService;

    public NotificationWebSocketController(
            SimpMessagingTemplate simpMessagingTemplate,
            NotificationService notificationService,
            UserService userService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @MessageMapping("private-notifications")
    public void receiveNotification(@Payload CreateNotificationRequest request) {

        User receiverUser = this.userService.getUserById(request.getReceiverId());

        if (!receiverUser.getSetting().getNotifications()) {
            return;
        }

        CreateNotificationDto notification = this.notificationService.createNotification(request);

        NotificationDto receiverNotification = this.notificationService
                .getNotification(notification.getReceiverNotification().getId());

        NotificationDto senderNotification = this.notificationService
                .getNotification(notification.getSenderNotification().getId());

        if (request.getSenderId() != request.getReceiverId()) {
            this.simpMessagingTemplate.convertAndSendToUser(String.valueOf(request.getReceiverId()), "notifications",
                    receiverNotification);

        }
        this.simpMessagingTemplate.convertAndSendToUser(String.valueOf(request.getSenderId()), "notifications",
                senderNotification);

    }

}
