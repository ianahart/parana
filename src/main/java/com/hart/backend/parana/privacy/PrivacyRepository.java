package com.hart.backend.parana.privacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivacyRepository extends JpaRepository<Privacy, Long> {

    @Query(value = """
            SELECT p FROM Privacy p
            INNER JOIN p.blockedUser bu
            INNER JOIN p.blockedByUser bbu
            WHERE bu.id = :blockedUserId
            AND bbu.id = :blockedByUserId
                """)
    Privacy getExistingPrivacy(@Param("blockedUserId") Long blockedUserId,
            @Param("blockedByUserId") Long blockedByUserId);

}
