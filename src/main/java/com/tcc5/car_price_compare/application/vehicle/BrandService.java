package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.application.user.ConversionService;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.BrandNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.BrandRepository;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ConversionService conversionService;
    public BrandService(BrandRepository brandRepository, ConversionService conversionService) {
        this.brandRepository = brandRepository;
        this.conversionService = conversionService;
    }

    public BrandDTO findByUrlPathName(String urlPathName) {
        var brand = brandRepository.findByUrlPathName(urlPathName)
                .orElseThrow(() -> new BrandNotFoundException(urlPathName));

        return conversionService.convertToBrandDTO(brand);
    }
}