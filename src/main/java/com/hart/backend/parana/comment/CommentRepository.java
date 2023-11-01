package com.hart.backend.parana.comment;

import java.util.List;

import com.hart.backend.parana.comment.dto.CommentDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findTop5ByUserIdAndPostIdOrderByIdDesc(Long userId, Long postId);

    @Query(value = """
            SELECT new com.hart.backend.parana.comment.dto.CommentDto(
            c.id AS id, u.id AS userId, po.id AS postId, u.fullName AS fullName,
            pr.avatarUrl AS avatarUrl, c.text AS text, c.createdAt AS createdAt
            ) FROM Comment c
            INNER JOIN c.user u
            INNER JOIN c.post po
            INNER JOIN c.user.profile pr
            WHERE po.id = :postId
            """)
    Page<CommentDto> getComments(@Param("postId") Long postId, @Param("pageable") Pageable pageable);

}
