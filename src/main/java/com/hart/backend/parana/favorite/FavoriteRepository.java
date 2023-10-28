package com.hart.backend.parana.favorite;

import com.hart.backend.parana.favorite.dto.FavoriteDto;
import com.hart.backend.parana.favorite.dto.FullFavoriteDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query(value = """
            SELECT new com.hart.backend.parana.favorite.dto.FullFavoriteDto(
             f.id AS id, t.id AS teacherId, p.id AS profileId,
             p.avatarUrl AS avatarUrl, t.fullName AS fullName, f.createdAt AS createdAt
            ) FROM Favorite f
            INNER JOIN f.user u
            INNER JOIN f.teacher t
            INNER JOIN f.teacher.profile p
            WHERE u.id = :userId
            """)
    Page<FullFavoriteDto> getFavorites(@Param("userId") Long userId, @Param("pageable") Pageable pageable);

    @Query(value = """
            SELECT new com.hart.backend.parana.favorite.dto.FavoriteDto(
            f.id AS id, f.isFavorited AS isFavorited
            ) FROM Favorite f
            INNER JOIN f.user u
            INNER JOIN f.teacher t
            WHERE u.id = :userId
            AND t.id = :teacherId
            """)
    FavoriteDto getFavorite(@Param("userId") Long userId, @Param("teacherId") Long teacherId);

    @Query(value = """
             SELECT EXISTS(SELECT 1 FROM Favorite f
              INNER JOIN f.user u
              INNER JOIN f.teacher t
              WHERE u.id = :userId
              AND t.id = :teacherId
              )
            """)
    boolean alreadyFavoritedFavorite(@Param("userId") Long userId, @Param("teacherId") Long teacherId);

}
