package com.tcc5.car_price_compare.domain.user.converters;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.request.user.FavoriteRequestDTO;
import com.tcc5.car_price_compare.domain.user.features.Favorite;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.VehicleNotFoundException;
import com.tcc5.car_price_compare.repositories.vehicle.VehicleRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FavoriteRequestDTOToFavoriteConverter implements Converter<FavoriteRequestDTO, Favorite> {

    private final VehicleRepository vehicleRepository;

    public FavoriteRequestDTOToFavoriteConverter(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Favorite convert(FavoriteRequestDTO source) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vehicle vehicle = this.vehicleRepository.findById(source.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(source.vehicleId()));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setVehicle(vehicle);
        return favorite;
    }
}
