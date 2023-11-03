package com.hart.backend.parana.message;

import com.hart.backend.parana.message.dto.MessageDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = """
            SELECT new com.hart.backend.parana.message.dto.MessageDto(
             m.id AS id, m.createdAt AS createdAt, s.id AS senderUserId,
             r.id AS receiverUserId, s.fullName AS senderFullName,
             r.fullName AS receiverFullName, rp.avatarUrl AS receiverAvatarUrl,
            sp.avatarUrl AS senderAvatarUrl, m.text AS text
            ) FROM Message m
            INNER JOIN m.sender s
            INNER JOIN m.sender.profile sp
            INNER JOIN m.receiver r
            INNER JOIN m.receiver.profile rp
            WHERE m.id = :messageId

                """)
    MessageDto getMessage(@Param("messageId") Long messageId);

    @Query(value = """
            SELECT new com.hart.backend.parana.message.dto.MessageDto(
             m.id AS id, m.createdAt AS createdAt, s.id AS senderUserId,
             r.id AS receiverUserId, s.fullName AS senderFullName,
             r.fullName AS receiverFullName, rp.avatarUrl AS receiverAvatarUrl,
            sp.avatarUrl AS senderAvatarUrl, m.text AS text
            ) FROM Message m
            INNER JOIN m.sender s
            INNER JOIN m.sender.profile sp
            INNER JOIN m.receiver r
            INNER JOIN m.receiver.profile rp
            WHERE (r.id = :connectionUserId AND s.id = :userId)
            OR (r.id = :userId AND s.id = :connectionUserId)

                """)
    Page<MessageDto> getMessages(
            @Param("userId") Long userId,
            @Param("connectionUserId") Long connectionUserId,
            @Param("pageable") Pageable pageable);
}
