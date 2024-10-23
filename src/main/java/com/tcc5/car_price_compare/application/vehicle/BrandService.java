package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.application.user.ConversionService;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.BrandNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.ModelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ConversionService conversionService;
    private final ModelRepository modelRepository;
    public BrandService(BrandRepository brandRepository, ConversionService conversionService, ModelRepository modelRepository) {
        this.brandRepository = brandRepository;
        this.conversionService = conversionService;
        this.modelRepository = modelRepository;
    }

    public BrandDTO findByUrlPathName(String urlPathName) {
        var brand = brandRepository.findFirstByUrlPathName(urlPathName)
                .orElseThrow(() -> new BrandNotFoundException(urlPathName));

        return conversionService.convertToBrandDTO(brand);
    }

    public Page<ModelDTO> findModelsByBrandId(UUID brandId, Pageable pageable) {
        Page<Model> models = modelRepository.findModelsByBrandId(brandId, pageable);
        List<ModelDTO> modelDTOs = models.map(conversionService::convertToModelDTO).getContent();

        return new PageImpl<ModelDTO>(modelDTOs, pageable, models.getTotalElements());
    }
}