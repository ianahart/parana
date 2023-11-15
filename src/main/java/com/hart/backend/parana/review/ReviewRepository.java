package com.hart.backend.parana.review;

import com.hart.backend.parana.review.dto.LatestReviewDto;
import com.hart.backend.parana.review.dto.PartialReviewDto;
import com.hart.backend.parana.review.dto.ReviewDto;
import com.hart.backend.parana.review.dto.TopReviewDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = """
            SELECT COUNT(*) FROM Review r
            WHERE r.rating = 5
            """)
    Long getFiveStarReviews();

    @Query(value = """
            SELECT new com.hart.backend.parana.review.dto.TopReviewDto(
            t.id AS userId, t.firstName AS fullName, p.avatarUrl AS avatarUrl,
            AVG(r.rating) AS averageRating, COUNT(t.id) AS numReviews
            ) FROM Review r
            INNER JOIN r.teacher t
            INNER JOIN r.teacher.profile p
            GROUP BY t.id, t.fullName, p.avatarUrl
            ORDER BY COUNT(t.id) DESC
            """)
    Page<TopReviewDto> getTopReviews(@Param("pageable") Pageable pageable);

    @Query(value = """
            SELECT new com.hart.backend.parana.review.dto.LatestReviewDto(
            r.id AS id, u.id AS userId, u.firstName AS fullName, p.avatarUrl AS avatarUrl,
            r.review AS review, r.rating AS rating
            ) FROM Review r
            INNER JOIN r.user u
            INNER JOIN r.user.profile p
            """)
    Page<LatestReviewDto> getLatestReviews(@Param("pageable") Pageable pageable);

    @Query(value = """
            SELECT new com.hart.backend.parana.review.dto.PartialReviewDto(
            r.review AS review, r.rating AS rating
            ) FROM Review r
            WHERE r.id = :reviewId
            """)
    PartialReviewDto getPartialReview(@Param("reviewId") Long reviewId);

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
