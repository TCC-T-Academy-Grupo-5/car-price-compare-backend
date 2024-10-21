package com.tcc5.car_price_compare.infra.fipedata.converter;

import com.tcc5.car_price_compare.domain.vehicle.FipePrice;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.infra.fipedata.dto.FipePriceJsonDto;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.VehicleRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FipePriceJsonDtoToFipePriceConverter implements Converter<FipePriceJsonDto, FipePrice> {

    private final VehicleRepository vehicleRepository;

    public FipePriceJsonDtoToFipePriceConverter(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public FipePrice convert(FipePriceJsonDto source) {
        Vehicle vehicle = this.vehicleRepository.findById(UUID.fromString(source.version_id())).orElseThrow(() -> {
            return new RuntimeException("Error converting FipePriceJsonDto to FipePrice: no vehicle with id " + source.version_id());
        });

        FipePrice fipePrice = new FipePrice();

        fipePrice.setId(UUID.fromString(source.id()));
        fipePrice.setPrice(source.price());
        fipePrice.setMonth(source.month());
        fipePrice.setYear(source.year());
        fipePrice.setVehicle(vehicle);
        return fipePrice;
    }
}
