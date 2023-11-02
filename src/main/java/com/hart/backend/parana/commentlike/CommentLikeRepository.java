package com.hart.backend.parana.commentlike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    CommentLike findCommentLikeByCommentIdAndUserId(Long commentId, Long userId);

    long countByCommentId(Long commentId);

    @Query(value = """
             SELECT EXISTS(SELECT 1 FROM CommentLike cl
              INNER JOIN cl.comment c
              INNER JOIN cl.user u
              WHERE c.id = :commentId
              AND u.id = :userId
             )
            """)
    boolean alreadyLikedComment(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
