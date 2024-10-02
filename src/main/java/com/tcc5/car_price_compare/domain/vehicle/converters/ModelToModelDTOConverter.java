package com.tcc5.car_price_compare.domain.vehicle.converters;

import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.ModelNotFoundException;
import com.tcc5.car_price_compare.repositories.vehicle.ModelRepository;
import org.springframework.core.convert.converter.Converter;

public class ModelToModelDTOConverter implements Converter<Model, ModelDTO> {
    private final ModelRepository modelRepository;

    public ModelToModelDTOConverter(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }
    @Override
    public ModelDTO convert(Model source) {
        return this.modelRepository.findModelDTOById(source.getId())
                .orElseThrow(() -> new ModelNotFoundException(source.getId()));
    }
}
