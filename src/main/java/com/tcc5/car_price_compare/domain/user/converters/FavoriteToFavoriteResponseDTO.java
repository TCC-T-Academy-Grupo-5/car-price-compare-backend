package com.tcc5.car_price_compare.domain.user.converters;

import com.tcc5.car_price_compare.domain.response.user.FavoriteResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Favorite;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FavoriteToFavoriteResponseDTO implements Converter<Favorite, FavoriteResponseDTO> {
    @Override
    public FavoriteResponseDTO convert(Favorite source) {
        return FavoriteResponseDTO.builder()
                .favoriteId(source.getId())
                .build();
    }
}
