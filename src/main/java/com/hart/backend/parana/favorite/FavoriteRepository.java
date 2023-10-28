package com.hart.backend.parana.favorite;

import com.hart.backend.parana.favorite.dto.FavoriteDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

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
