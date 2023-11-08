package com.hart.backend.parana.privacy;

import com.hart.backend.parana.privacy.dto.PrivacyDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivacyRepository extends JpaRepository<Privacy, Long> {

    @Query(value = """
            SELECT EXISTS(
            SELECT 1 FROM Privacy p
            INNER JOIN p.blockedUser bu
            INNER JOIN p.blockedByUser bbu
            WHERE p.comments = true
            AND bu.id = :authorId
            AND bbu.id = :ownerId
            )
            """)
    boolean blockedFromCreatingComments(@Param("authorId") Long authorId, @Param("ownerId") Long ownerId);

    @Query(value = """
            SELECT EXISTS(
            SELECT 1 FROM Privacy p
            INNER JOIN p.blockedUser bu
            INNER JOIN p.blockedByUser bbu
            WHERE p.posts = true
            AND bu.id = :authorId
            AND bbu.id = :ownerId
            )
            """)
    boolean blockedFromCreatingPosts(@Param("authorId") Long authorId, @Param("ownerId") Long ownerId);

    @Query(value = """
            SELECT p FROM Privacy p
            INNER JOIN p.blockedUser bu
            INNER JOIN p.blockedByUser bbu
            WHERE bu.id = :blockedUserId
            AND bbu.id = :blockedByUserId
                """)
    Privacy getExistingPrivacy(@Param("blockedUserId") Long blockedUserId,
            @Param("blockedByUserId") Long blockedByUserId);

    @Query(value = """
            SELECT new com.hart.backend.parana.privacy.dto.PrivacyDto(
            p.id AS id, bu.id AS blockedUserId, bup.avatarUrl AS avatarUrl,
            bu.fullName AS fullName, p.messages AS messages, p.posts AS posts,
            p.comments AS comments, p.updatedAt AS updatedAt
            ) FROM Privacy p
            INNER JOIN p.blockedByUser bbu
            INNER JOIN p.blockedUser bu
            INNER JOIN p.blockedUser.profile bup
            WHERE bbu.id = :userId
                """)
    Page<PrivacyDto> getPrivacies(@Param("userId") Long userId, @Param("pageable") Pageable pageable);
}
