package com.tcc5.car_price_compare.domain.price.converters;

import org.springframework.core.convert.converter.Converter;
import com.tcc5.car_price_compare.domain.price.dto.FipePriceDTO;
import com.tcc5.car_price_compare.domain.vehicle.FipePrice;
import org.springframework.stereotype.Component;

@Component
public class FipePriceToFipePriceDTOConverter implements Converter<FipePrice, FipePriceDTO> {
    @Override
    public FipePriceDTO convert(FipePrice source) {
        return new FipePriceDTO(
            source.getPrice(),
            source.getVehicle().getId().toString(),
            source.getMonth().toString()
        );
    }
}
