package com.hart.backend.parana.setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

    @Query(value = """
             SELECT EXISTS(
                SELECT 1
                FROM Setting s
                INNER JOIN s.user u
                WHERE u.id = :userId
                )
            """)
    boolean settingsAlreadyCreated(@Param("userId") Long userId);
}
