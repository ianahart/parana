package com.hart.backend.parana.recommendation;

import com.hart.backend.parana.recommendation.dto.RecommendationDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    @Query(value = """
            SELECT new com.hart.backend.parana.recommendation.dto.RecommendationDto(
              a.firstName AS firstName, a.fullName AS fullName, p.avatarUrl AS avatarUrl,
              r.id AS id, t.id AS teacherId, a.id AS authorId, r.words AS words,
              r.recommendation AS recommendation, r.createdAt AS createdAt
            ) FROM Recommendation r
            INNER JOIN r.author a
            INNER JOIN r.author.profile p
            INNER JOIN r.teacher t
            WHERE t.id = :teacherId
            """)
    Page<RecommendationDto> getRecommendations(@Param("teacherId") Long teacherId,
            @Param("pageable") Pageable pageable);

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
