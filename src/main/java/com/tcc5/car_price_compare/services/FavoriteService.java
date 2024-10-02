package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.user.features.Favorite;
import com.tcc5.car_price_compare.repositories.FavoriteRepository;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Favorite save(Favorite favorite) {
        return this.favoriteRepository.save(favorite);
    }
}
