package com.hart.backend.parana.post;

import java.util.List;

import com.hart.backend.parana.post.dto.PostDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findTop5ByAuthorIdAndOwnerIdOrderByIdDesc(Long authorId, Long ownerId);

    @Query(value = """
            SELECT new com.hart.backend.parana.post.dto.PostDto(
            p.id AS id, o.id AS ownerId, p.text AS text, p.gif AS gif,
            p.fileUrl AS fileUrl, p.createdAt AS createdAt, p.isEdited AS isEdited,
            a.fullName AS authorFullName, ap.avatarUrl AS authorAvatarUrl,
            a.id AS authorId
            ) FROM Post p
            INNER JOIN p.owner o
            INNER JOIN p.author a
            INNER JOIN p.author.profile ap
            WHERE o.id = :ownerId
            """)
    Page<PostDto> getPosts(@Param("ownerId") Long ownerId, @Param("pageable") Pageable pageable);
}
