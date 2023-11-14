package com.hart.backend.parana.connection;

import java.util.List;

import com.hart.backend.parana.connection.dto.ActiveConnectionDto;
import com.hart.backend.parana.connection.dto.ConnectionDto;
import com.hart.backend.parana.connection.dto.MinimalConnectionDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query(value = """
                SELECT new com.hart.backend.parana.connection.dto.ActiveConnectionDto(
            r.id AS userId, r.loggedIn AS loggedIn
                ) FROM Connection c
                INNER JOIN c.sender s
                INNER JOIN c.receiver r
                WHERE s.id = :userId
                AND accepted = :accepted
                AND r.loggedIn = true
                    """)
    List<ActiveConnectionDto> getActiveUserConnections(
            @Param("userId") Long userId,
            @Param("accepted") Boolean accepted);

    @Query(value = """
                SELECT new com.hart.backend.parana.connection.dto.ActiveConnectionDto(
            s.id AS userId, s.loggedIn AS loggedIn
                ) FROM Connection c
                INNER JOIN c.sender s
                INNER JOIN c.receiver r
                WHERE r.id = :userId
                AND accepted = :accepted
                AND s.loggedIn = true
                    """)
    List<ActiveConnectionDto> getActiveTeacherConnections(
            @Param("userId") Long userId,
            @Param("accepted") Boolean accepted);

    @Query(value = """
                SELECT new com.hart.backend.parana.connection.dto.MinimalConnectionDto(
            r.id AS userId
                ) FROM Connection c
                INNER JOIN c.sender s
                INNER JOIN c.receiver r
                WHERE s.id = :userId
                AND accepted = :accepted
                    """)
    List<MinimalConnectionDto> getAllUserConnections(
            @Param("userId") Long userId,
            @Param("accepted") Boolean accepted);

    @Query(value = """
            SELECT EXISTS(SELECT 1 FROM Connection c
            INNER JOIN c.sender s
            INNER JOIN c.receiver r
            WHERE s.id = :senderId
            AND r.id = :receiverId
            AND accepted = true
            )
            """)
    boolean isConnected(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Query(value = """
            SELECT EXISTS(SELECT 1 FROM Connection c
            INNER JOIN c.sender s
            INNER JOIN c.receiver r
            WHERE s.id = :senderId
            AND r.id = :receiverId
            AND pending = true
            )
            """)
    boolean isPending(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Query(value = """
            SELECT new com.hart.backend.parana.connection.dto.ConnectionDto(
            s.loggedIn AS loggedIn, s.fullName AS fullName, p.avatarUrl AS avatarUrl, c.createdAt AS createdAt,
             s.id AS userId, c.id AS id, p.id AS profileId
            ) FROM Connection c
            INNER JOIN c.receiver r
            INNER JOIN c.sender s
            INNER JOIN c.sender.profile p
            WHERE r.id = :userId
            AND accepted = :accepted
            AND (:query IS NULL OR LOWER(s.fullName) LIKE %:query%)
                """)

    Page<ConnectionDto> getTeacherConnections(
            @Param("userId") Long userId,
            @Param("accepted") Boolean accepted,
            @Param("query") String query,
            Pageable pageable);

    @Query(value = """
            SELECT new com.hart.backend.parana.connection.dto.ConnectionDto(
            r.loggedIn AS loggedIn, r.fullName AS fullName, p.avatarUrl AS avatarUrl, c.createdAt AS createdAt,
             r.id AS userId, c.id AS id, p.id AS profileId
            ) FROM Connection c
            INNER JOIN c.sender s
            INNER JOIN c.receiver r
            INNER JOIN c.receiver.profile p
            WHERE s.id = :userId
            AND accepted = :accepted
            AND (:query IS NULL OR LOWER(r.fullName) LIKE %:query%)
                """)

    Page<ConnectionDto> getUserConnections(
            @Param("userId") Long userId,
            @Param("accepted") Boolean accepted,
            @Param("query") String query,
            Pageable pageable);

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
