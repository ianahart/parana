package com.hart.backend.parana.recentsearch;

import java.util.List;

import com.hart.backend.parana.recentsearch.dto.RecentSearchDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {

    @Query(value = """
            SELECT EXISTS(SELECT 1 FROM RecentSearch r
            INNER JOIN r.user u
            WHERE u.id = :userId
            AND r.term = :searchTerm
            )
            """)
    boolean alreadySearched(@Param("userId") Long userId, @Param("searchTerm") String searchTerm);

    @Query(value = """
              SELECT r.id AS recentSearchId, term FROM recent_search r
              INNER JOIN _user u on u.id = :userId
              ORDER BY recentSearchId DESC LIMIT :limit
            """, nativeQuery = true)
    List<RecentSearchDto> getRecentSearches(@Param("userId") Long userId, @Param("limit") Integer limit);
}
