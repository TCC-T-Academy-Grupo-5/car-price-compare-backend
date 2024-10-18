package com.tcc5.car_price_compare.domain.vehicle.converters;

import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BrandToBrandDTOConverter implements Converter<Brand, BrandDTO> {

    @Override
    public BrandDTO convert(Brand source) {
        return new BrandDTO(
                source.getId(),
                source.getName(),
                source.getUrlPathName(),
                source.getImageUrl(),
                source.getVehicleType()
        );
    }
}
