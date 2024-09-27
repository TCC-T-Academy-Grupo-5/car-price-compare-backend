package com.tcc5.car_price_compare.domain.vehicle.converters;

import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VehicleToVehicleDTOConverter implements Converter<Vehicle, VehicleDTO> {

    @Override
    public VehicleDTO convert(Vehicle source) {
        UUID id = source.getId();
        String fipeCode = source.getFipeCode();
        String yearName = source.getYear().getName();
        String modelName = source.getYear().getModel().getName();
        String brandName = source.getYear().getModel().getBrand().getName();
        VehicleType vehicleType = source.getYear().getModel().getBrand().getVehicleType();

        return new VehicleDTO(id, fipeCode, yearName, modelName, brandName, vehicleType);
    }
}
