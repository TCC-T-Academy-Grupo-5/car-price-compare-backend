package com.tcc5.car_price_compare.application.vehicle.model;

import com.tcc5.car_price_compare.application.ConversionService;
import com.tcc5.car_price_compare.application.vehicle.statistics.StatisticService;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.ModelNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.ModelRepository;
import com.tcc5.car_price_compare.infra.persistence.specifications.ModelSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModelService {

    private final BrandRepository brandRepository;
    private final StatisticService statisticService;
    private final ModelRepository modelRepository;
    private final ConversionService conversionService;

    public ModelService(ModelRepository modelRepository, ConversionService conversionService, StatisticService statisticService, BrandRepository brandRepository) {
        this.modelRepository = modelRepository;
        this.conversionService = conversionService;
        this.statisticService = statisticService;
        this.brandRepository = brandRepository;
    }

    public Page<ModelDTO> findModelsByBrandId(UUID brandId, Pageable pageable) {
        Page<Model> models = modelRepository.findModelsByBrandId(brandId, pageable);
        List<ModelDTO> modelDTOs = models.map(conversionService::convertToModelDTO).getContent();
        return new PageImpl<>(modelDTOs, pageable, models.getTotalElements());
    }

    public Page<ModelDTO> findAll(String name, String brand, Pageable pageable) {
        Specification<Model> spec = Specification
                .where(ModelSpecification.hasModel(name))
                .and(ModelSpecification.hasBrand(brand));

        Page<Model> pagedModels = modelRepository.findAll(spec, pageable);
        List<ModelDTO> modelDTOs = modelsListToModelDTO(pagedModels.getContent());

        return new PageImpl<>(modelDTOs, pageable, pagedModels.getTotalElements());
    }

    public Model findByName(String name) {
        var model = modelRepository.findByName(name);
        if (model.isEmpty()) throw new ModelNotFoundException(name);
        return model.get();
    }

    @Transactional
    public Model add(AddModelDTO modelDTO) {
        Model model = new Model();
        BeanUtils.copyProperties(modelDTO, model);

        var brand = brandRepository.findByName(modelDTO.brandName());
        if (brand.isPresent()) {
            return modelRepository.save(model);
        } else {
            throw new RuntimeException("Brand not found");
        }
    }

    private void sendModelStatistics(String model) {
        if (model != null && !(model.isEmpty() || model.isBlank())) {
            Optional<Model> res = modelRepository.findByName(model);
            res.ifPresent(value -> statisticService.incrementSearch(value.getId(), EntityType.MODEL));
        }
    }

    private List<ModelDTO> modelsListToModelDTO(List<Model> models) {
        List<ModelDTO> modelsDto = new ArrayList<>();
        models.forEach(m -> modelsDto.add(conversionService.convertToModelDTO(m)));
        return modelsDto;
    }

    public List<OptionDTO> findOptionsByBrandId(UUID brandId) {
        return this.modelRepository.findOptionsByBrandId(brandId);
    }
}
