package com.hart.backend.parana.review;

import com.hart.backend.parana.review.dto.ReviewDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = """
             SELECT AVG(rating) FROM Review r
             INNER JOIN r.teacher t
             WHERE t.id = :teacherId
            """)
    Byte getAvgRating(@Param("teacherId") Long teacherId);

    @Query(value = """
            SELECT new com.hart.backend.parana.review.dto.ReviewDto(
             r.rating AS rating, r.review AS review, r.id AS id,
            u.id AS userId, p.avatarUrl AS avatarUrl, u.firstName AS firstName,
            r.isEdited as isEdited, u.fullName AS fullName
            ) FROM Review r
            INNER JOIN r.user u
            INNER JOIN r.user.profile p
            INNER JOIN r.teacher t
            WHERE t.id = :teacherId
            """)
    Page<ReviewDto> getReviews(@Param("teacherId") Long teacherId, Pageable pageable);

    @Query(value = """
            SELECT EXISTS(
            SELECT 1 FROM Review r
            INNER JOIN r.user u
            INNER JOIN r.teacher t
            WHERE u.id = :userId
            AND t.id = :teacherId
            )
                """)
    boolean duplicateReview(@Param("userId") Long userId, @Param("teacherId") Long teacherId);
}
