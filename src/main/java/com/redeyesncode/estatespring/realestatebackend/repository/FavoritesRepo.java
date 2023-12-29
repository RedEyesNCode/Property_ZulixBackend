package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.Favorites;
import com.redeyesncode.estatespring.realestatebackend.models.UserTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritesRepo extends CrudRepository<Favorites,Long> {
    @Query("SELECT f FROM Favorites f WHERE f.user = :user")
    List<Favorites> findByUser(UserTable user);

//    @Query("SELECT f FROM Favorites f WHERE f.user.userId = :userId")
//    List<Favorites> findFavoritesByUserId(@Param("userId") Long userId);
@Modifying
@Query("DELETE FROM Favorites f WHERE f.user = :user")
void deleteByUser(@Param("user") UserTable user);
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Favorites f WHERE f.user.userId = :userId")
//    void deleteFavoritesByUserId(@Param("userId") int userId);
}

