package com.tcc5.car_price_compare.application.user.favorite;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.exceptions.FavoriteNotFoundException;
import com.tcc5.car_price_compare.domain.user.features.Favorite;
import com.tcc5.car_price_compare.infra.persistence.repositories.FavoriteRepository;
import com.tcc5.car_price_compare.infra.persistence.specifications.FavoriteSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Page<Favorite> findAll(Integer pageNumber, Integer pageSize, Integer vehicleType) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Specification<Favorite> spec = Specification
                .where(FavoriteSpecification.hasVehicleType(vehicleType))
                .and(FavoriteSpecification.hasUser(currentUser));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return this.favoriteRepository.findAll(spec, pageable);
    }

    public Favorite findById(UUID favoriteId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.favoriteRepository
                .findByIdAndUser(favoriteId, currentUser)
                .orElseThrow(() -> new FavoriteNotFoundException(favoriteId));
    }

    public Favorite save(Favorite favorite) {
        return this.favoriteRepository.save(favorite);
    }

    public Favorite update(UUID favoriteId, Favorite favorite) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.favoriteRepository.findByIdAndUser(favoriteId, currentUser)
                .map(oldFavorite -> {
                    oldFavorite.setVehicle(favorite.getVehicle());
                    return this.favoriteRepository.save(oldFavorite);
                })
                .orElseThrow(() -> new FavoriteNotFoundException(favoriteId));
    }

    public void delete(UUID favoriteId) {
        this.favoriteRepository.delete(this.findById(favoriteId));
    }
}
