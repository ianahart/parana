package com.hart.backend.parana.notification;

import java.util.Map;
import java.util.HashMap;

import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.notification.dto.CreateNotificationDto;
import com.hart.backend.parana.notification.dto.NotificationDto;
import com.hart.backend.parana.notification.dto.NotificationPaginationDto;
import com.hart.backend.parana.notification.request.CreateNotificationRequest;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserService userService;

    public NotificationService(
            NotificationRepository notificationRepository,
            UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    private Map<String, String> populateNotificationText(User receiver, User sender, String type) {
        Map<String, String> hm = new HashMap<String, String>();
        String receiverText = "";
        String senderText = "";
        switch (type) {
            case "posts":
                receiverText = String.format("%s posted to your profile", sender.getFullName());
                senderText = String.format("You posted to %s's profile", receiver.getFullName());
                break;
            case "comments":
                receiverText = String.format("%s posted a comment on your post", sender.getFullName());
                senderText = String.format("You posted a comment on %s's post", receiver.getFullName());
                break;
        }

        hm.put("receiverText", receiverText);
        hm.put("senderText", senderText);
        return hm;
    }

    public CreateNotificationDto createNotification(CreateNotificationRequest request) {
        User receiver = this.userService.getUserById(request.getReceiverId());
        User sender = this.userService.getUserById(request.getSenderId());

        Map<String, String> notificationText = populateNotificationText(receiver, sender, request.getType());
        Notification receiverNotification = new Notification(notificationText.get("receiverText"), receiver, sender);
        Notification senderNotification = new Notification(notificationText.get("senderText"), sender, sender);

        this.notificationRepository.save(receiverNotification);
        this.notificationRepository.save(senderNotification);

        return new CreateNotificationDto(receiverNotification, senderNotification);
    }

    public NotificationDto getNotification(Long notificationId) {
        return this.notificationRepository.getNotification(notificationId);
    }

    public Page<NotificationDto> paginateNotifications(Long userId, int page, int pageSize, String direction) {
        int currentPage = MyUtil.paginate(page, direction);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        return this.notificationRepository.getNotifications(userId, pageable);
    }

    public NotificationPaginationDto<NotificationDto> getNotifications(Long userId, int page, int pageSize,
            String direction) {
        Page<NotificationDto> result = paginateNotifications(userId, page, pageSize, direction);

        return new NotificationPaginationDto<NotificationDto>(result.getContent(), result.getNumber(), pageSize,
                result.getTotalPages(),
                direction, result.getTotalElements());

    }

    private Notification getNotificationById(Long notificationId) {
        return this.notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotFoundException("Notification with the id " + notificationId + " was not found"));
    }

    public void deleteNotification(Long notificationId) {
        Notification notification = getNotificationById(notificationId);
        this.notificationRepository.delete(notification);

    }
}
