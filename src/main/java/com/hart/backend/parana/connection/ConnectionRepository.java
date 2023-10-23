package com.hart.backend.parana.connection;

import com.hart.backend.parana.connection.dto.ConnectionRequestDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query(value = """
            SELECT new com.hart.backend.parana.connection.dto.ConnectionRequestDto(
             s.fullName AS fullName, p.avatarUrl AS avatarUrl, c.createdAt AS createdAt,
             s.id AS userId, c.id AS id, p.id AS profileId
            ) FROM Connection c
            INNER JOIN c.receiver r
            INNER JOIN c.sender s
            INNER JOIN c.sender.profile p
            WHERE r.id = :userId
            AND accepted = false
                """)

    Page<ConnectionRequestDto> getTeacherConnectionRequests(@Param("userId") Long userId, Pageable pageable);

    @Query(value = """
            SELECT new com.hart.backend.parana.connection.dto.ConnectionRequestDto(
             r.fullName AS fullName, p.avatarUrl AS avatarUrl, c.createdAt AS createdAt,
             r.id AS userId, c.id AS id, p.id AS profileId
            ) FROM Connection c
            INNER JOIN c.sender s
            INNER JOIN c.receiver r
            INNER JOIN c.receiver.profile p
            WHERE s.id = :userId
            AND accepted = false
                """)

    Page<ConnectionRequestDto> getUserConnectionRequests(@Param("userId") Long userId, Pageable pageable);

    @Query(value = """
            SELECT EXISTS(SELECT 1 FROM Connection c
            INNER JOIN c.sender s
            INNER JOIN c.receiver r
            WHERE s.id = :senderId
            AND r.id = :receiverId
            )
            """)
    boolean duplicateConnection(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Query(value = """
             SELECT c FROM Connection c
             INNER JOIN c.sender s
             INNER JOIN c.receiver r
             WHERE s.id = :senderId
             AND r.id = :receiverId
            """)
    Connection getConnectionBySenderAndReceiver(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

}
