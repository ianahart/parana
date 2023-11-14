package com.hart.backend.parana.story;

import java.util.List;

import com.hart.backend.parana.story.dto.StoryDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findTop5ByUserIdOrderByIdDesc(Long userId);

    @Query(value = """
                SELECT new com.hart.backend.parana.story.dto.StoryDto(
                s.id AS id, u.id AS userId, s.expiresIn AS expiresIn,
                p.avatarUrl AS avatarUrl, u.fullName AS fullName,
                s.photoUrl AS photoUrl, s.text AS text, s.fontSize AS fontsize,
                s.duration AS duration, s.color AS color, s.background AS background,
                s.alignment AS alignment, s.createdAt AS createdAt
                ) FROM Story s
                INNER JOIN s.user u
                INNER JOIN s.user.profile p
                WHERE u.id = :userId
            """)
    List<StoryDto> getStories(@Param("userId") Long userId);

    @Query(value = """
                SELECT new com.hart.backend.parana.story.dto.StoryDto(
                s.id AS id, u.id AS userId, s.expiresIn AS expiresIn,
                p.avatarUrl AS avatarUrl, u.fullName AS fullName,
                s.photoUrl AS photoUrl, s.text AS text, s.fontSize AS fontsize,
                s.duration AS duration, s.color AS color, s.background AS background,
                s.alignment AS alignment, s.createdAt AS createdAt
                ) FROM Story s
                INNER JOIN s.user u
                INNER JOIN s.user.profile p
                WHERE s.id = :storyId
            """)
    StoryDto getStory(@Param("storyId") Long storyId);

}
