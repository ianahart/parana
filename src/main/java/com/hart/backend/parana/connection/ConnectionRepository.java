package com.hart.backend.parana.connection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

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
