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

    @Query(value = """
             SELECT new com.hart.backend.parana.user.dto.TeacherDto(
             p.firstLessonFree AS firstLessonFree, p.avatarUrl AS avatarUrl, p.bio AS bio, u.id AS userId, p.id AS profileId, p.perHour AS perHour,
              u.firstName AS firstName, p.city AS city, p.state AS state
             ) FROM User u
             INNER JOIN u.profile p
             WHERE u.role = 'TEACHER'
            """)
    Page<TeacherDto> retrieveTeachers(@Param("paging") Pageable paging);

    @Query(value = """
             SELECT new com.hart.backend.parana.user.dto.TeacherDto(
              p.avatarUrl AS avatarUrl, p.bio AS bio, u.id AS userId, p.id AS profileId,
              u.firstName AS firstName, p.city AS city, p.state AS state
             ) FROM User u
             INNER JOIN u.profile p
             WHERE u.role = 'USER'
            """)
    Page<UserDto> retrieveUsers(@Param("paging") Pageable paging);
}
