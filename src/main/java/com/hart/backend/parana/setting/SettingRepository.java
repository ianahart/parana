package com.hart.backend.parana.setting;

import com.hart.backend.parana.setting.dto.SettingDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

    @Query(value = """
            SELECT new com.hart.backend.parana.setting.dto.SettingDto(
             s.id AS id, u.id AS userId, s.createdAt AS createdAt,
             s.rememberMe AS rememberMe, s.blockMessages AS blockMessages,
            s.blockComments AS blockComments, s.blockPosts AS blockPosts,
            s.ipAddress AS ipAddress, s.passwordUpdatedOn AS passwordUpdatedOn
            ) FROM Setting s
            INNER JOIN s.user u
            WHERE s.id = :settingId
            """)
    SettingDto getSettings(@Param("settingId") Long settingId);

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
