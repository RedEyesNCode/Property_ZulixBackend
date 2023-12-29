package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.Favorites;
import com.redeyesncode.estatespring.realestatebackend.models.UserTable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritesRepo extends JpaRepository<Favorites,Long> {
    @Query("SELECT f FROM Favorites f WHERE f.user = :user")
    List<Favorites> findByUser(UserTable user);
}
