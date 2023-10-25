package com.hart.backend.parana.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

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
