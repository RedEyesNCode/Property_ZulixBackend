package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepo extends JpaRepository<Favorites,Long> {
}
