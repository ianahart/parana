package com.hart.backend.parana.notification;

import com.hart.backend.parana.notification.dto.NotificationDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = """
                SELECT new com.hart.backend.parana.notification.dto.NotificationDto(
                n.id AS id, r.id AS receiverId, s.id AS senderId, n.text AS text,
                s.fullName AS fullName, p.avatarUrl AS avatarUrl, n.createdAt AS createdAt
                ) FROM Notification n
                 INNER JOIN n.receiver r
                INNER JOIN n.sender s
                INNER JOIN n.sender.profile p
                WHERE r.id = :userId
            """)
    Page<NotificationDto> getNotifications(@Param("userId") Long userId, @Param("pageable") Pageable pageable);

    @Query(value = """
                SELECT new com.hart.backend.parana.notification.dto.NotificationDto(
                n.id AS id, r.id AS receiverId, s.id AS senderId, n.text AS text,
                s.fullName AS fullName, p.avatarUrl AS avatarUrl, n.createdAt AS createdAt
                ) FROM Notification n
                INNER JOIN n.receiver r
                INNER JOIN n.sender s
                INNER JOIN n.sender.profile p
                WHERE n.id = :notificationId
            """)
    NotificationDto getNotification(@Param("notificationId") Long notificationId);
}
