package com.tcc5.car_price_compare.repositories;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.features.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID>, JpaSpecificationExecutor<Favorite> {

    Optional<Favorite> findByIdAndUser(UUID favoriteId, User user);
}
