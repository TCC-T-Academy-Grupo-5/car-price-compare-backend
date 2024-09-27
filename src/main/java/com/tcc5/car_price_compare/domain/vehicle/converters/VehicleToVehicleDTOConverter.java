package com.tcc5.car_price_compare.domain.vehicle.converters;

import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.VehicleNotFoundException;
import com.tcc5.car_price_compare.repositories.vehicle.VehicleRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VehicleToVehicleDTOConverter implements Converter<Vehicle, VehicleDTO> {

    private final VehicleRepository vehicleRepository;

    public VehicleToVehicleDTOConverter(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleDTO convert(Vehicle source) {
        return this.vehicleRepository.findVehicleDTOById(source.getId())
                .orElseThrow(() -> new VehicleNotFoundException(source.getId()));
    }
}
