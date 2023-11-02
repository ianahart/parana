package com.hart.backend.parana.replycomment;

import com.hart.backend.parana.replycomment.dto.ReplyCommentDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {

    @Query(value = """
             SELECT EXISTS(SELECT 1
                FROM ReplyComment rc
                INNER JOIN rc.comment c
                WHERE c.id = :commentId
                )
            """)
    boolean replyCommentsExist(@Param("commentId") Long commentId);

    @Query(value = """
            SELECT new com.hart.backend.parana.replycomment.dto.ReplyCommentDto(
            rc.id AS id, u.id AS userId, p.avatarUrl AS avatarUrl, rc.text AS text,
            rc.createdAt AS createdAt, u.fullName AS fullName
            ) FROM ReplyComment rc
            INNER JOIN rc.user u
            INNER JOIN rc.user.profile p
            INNER JOIN rc.comment c
            WHERE c.id = :commentId
                """)
    Page<ReplyCommentDto> getReplyComments(@Param("commentId") Long commentId, @Param("pageable") Pageable pageable);
}
