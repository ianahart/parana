package com.hart.backend.parana.profile;

import com.hart.backend.parana.profile.dto.TeacherProfileDto;
import com.hart.backend.parana.profile.dto.UserProfileDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query(value = """
                SELECT new com.hart.backend.parana.profile.dto.UserProfileDto(
                 u.role AS role, u.id AS userId, p.id AS id, p.bio AS bio, p.city AS city,
                 p.homeMountain AS homeMountain, p.stance AS stance,
                 p.state AS state, p.terrain AS terrain, p.travelUpTo AS travelUpTo,
                p.yearsSnowboarding AS yearsSnowboarding, p.avatarUrl AS avatarUrl
                ) FROM Profile p
                INNER JOIN p.user u
                WHERE p.id = :profileId
            """)

    UserProfileDto retrieveUser(@Param("profileId") Long profileId);

    @Query(value = """
                SELECT new com.hart.backend.parana.profile.dto.TeacherProfileDto(
                u.role AS role, u.id AS userId, p.id AS id, p.bio AS bio, p.city AS city, p.firstLessonFree AS firstLessonFree,
                 p.homeMountain AS homeMountain, p.perHour AS perHour, p.stance AS stance,
                 p.state AS state, p.tags AS tags, p.terrain AS terrain, p.travelUpTo AS travelUpTo,
                p.yearsSnowboarding AS yearsSnowboarding, p.avatarUrl AS avatarUrl
                ) FROM Profile p
                INNER JOIN p.user u
                WHERE p.id = :profileId
            """)

    TeacherProfileDto retrieveTeacher(@Param("profileId") Long profileId);
}
