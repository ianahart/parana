package com.hart.backend.parana.user;

import java.util.Optional;

import com.hart.backend.parana.user.dto.TeacherDto;
import com.hart.backend.parana.user.dto.UserDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    String HAVERSINE_FORMULA = "(3961 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) *" +
            " cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude))))";

    @Query("SELECT new com.hart.backend.parana.user.dto.TeacherDto(p.firstLessonFree AS firstLessonFree, p.avatarUrl AS avatarUrl, p.bio AS bio, u.id AS userId, p.id AS profileId, p.perHour AS perHour, u.firstName AS firstName, p.city AS city, p.state AS state)  FROM User u INNER JOIN u.profile p WHERE u.role = 'TEACHER' AND p.bio <> NULL              AND (:rate IS NULL OR CAST (p.perHour AS integer) <= :rate) AND "
            + HAVERSINE_FORMULA + " < :distance ORDER BY " + HAVERSINE_FORMULA + " DESC")
    Page<TeacherDto> findTeachersWithinDistance(@Param("latitude") double latitude,
            @Param("longitude") Double longitude,
            @Param("distance") Double distanceWithInMi, @Param("rate") Integer rate, @Param("paging") Pageable paging);

    @Query(value = """
             SELECT new com.hart.backend.parana.user.dto.TeacherDto(
             p.firstLessonFree AS firstLessonFree, p.avatarUrl AS avatarUrl, p.bio AS bio, u.id AS userId, p.id AS profileId, p.perHour AS perHour,
              u.firstName AS firstName, p.city AS city, p.state AS state
             ) FROM User u
             INNER JOIN u.profile p
             WHERE u.role = 'TEACHER'
             AND p.bio <> NULL
             AND (:rate IS NULL OR CAST (p.perHour AS integer) <= :rate)
            """)
    Page<TeacherDto> retrieveTeachers(@Param("paging") Pageable paging, @Param("rate") Integer rate);

    @Query(value = """
             SELECT new com.hart.backend.parana.user.dto.UserDto(
              p.avatarUrl AS avatarUrl, p.bio AS bio, u.id AS userId, p.id AS profileId,
              u.firstName AS firstName, p.city AS city, p.state AS state
             ) FROM User u
             INNER JOIN u.profile p
             WHERE u.role = 'USER'
            """)
    Page<UserDto> retrieveUsers(@Param("paging") Pageable paging);
}
