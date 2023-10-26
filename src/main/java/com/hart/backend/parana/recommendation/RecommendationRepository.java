package com.hart.backend.parana.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    @Query(value = """
            SELECT EXISTS(SELECT 1 FROM Recommendation r
              INNER JOIN r.author a
              INNER JOIN r.teacher t
              WHERE a.id = :authorId
              AND  t.id = :teacherId
            )
            """)
    boolean hasDuplicateRecommendation(@Param("authorId") Long authorId, @Param("teacherId") Long teacherId);
}
