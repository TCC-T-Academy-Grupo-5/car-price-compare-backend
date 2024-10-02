package com.tcc5.car_price_compare.domain.vehicle.converters;

import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.BrandNotFoundException;
import com.tcc5.car_price_compare.repositories.vehicle.BrandRepository;
import org.springframework.core.convert.converter.Converter;

public class BrandToBrandDTOConverter implements Converter<Brand, BrandDTO> {

    private final BrandRepository brandRepository;

    public BrandToBrandDTOConverter(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandDTO convert(Brand source) {
        return this.brandRepository.findBrandDTOById(source.getId())
                .orElseThrow(() -> new BrandNotFoundException(source.getId()));
    }
}
