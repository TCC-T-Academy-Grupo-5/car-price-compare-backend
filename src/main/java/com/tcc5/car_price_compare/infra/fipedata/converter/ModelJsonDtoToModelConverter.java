package com.tcc5.car_price_compare.infra.fipedata.converter;

import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.infra.fipedata.dto.ModelJsonDto;
import com.tcc5.car_price_compare.repositories.vehicle.BrandRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModelJsonDtoToModelConverter implements Converter<ModelJsonDto, Model> {

    private final BrandRepository brandRepository;

    public ModelJsonDtoToModelConverter(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Model convert(ModelJsonDto source) {
        Brand brand = this.brandRepository.findById(source.brand_id()).orElseThrow(() -> {
            return new RuntimeException("Error converting ModelJsonDto to Model: no brand with id " + source.brand_id());
        });

        Model model = new Model();
        model.setId(source.id());
        model.setName(source.name());
        model.setUrlPathName(source.url_path_name());
        model.setBrand(brand);

        return model;
    }
}
