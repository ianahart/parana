package com.hart.backend.parana.postlike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    PostLike findPostLikeByPostIdAndUserId(Long postId, Long userId);

    long countByPostId(Long postId);

    @Query(value = """
            SELECT EXISTS(SELECT 1
                FROM PostLike pl
                INNER JOIN pl.user u
                INNER JOIN pl.post p
                WHERE u.id = :userId
                AND p.id = :postId
                )
            """)
    boolean hasAlreadyLikedPost(@Param("userId") Long userId, @Param("postId") Long postId);
}
