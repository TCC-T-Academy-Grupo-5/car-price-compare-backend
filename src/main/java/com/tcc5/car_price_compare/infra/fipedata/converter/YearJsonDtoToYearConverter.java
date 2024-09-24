package com.tcc5.car_price_compare.infra.fipedata.converter;

import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.infra.fipedata.dto.YearJsonDto;
import com.tcc5.car_price_compare.repositories.vehicle.ModelRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class YearJsonDtoToYearConverter implements Converter<YearJsonDto, Year> {
    private final ModelRepository modelRepository;

    public YearJsonDtoToYearConverter(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public Year convert(YearJsonDto source) {
        Model model = this.modelRepository.findById(source.model_id()).orElseThrow(() -> {
            return new RuntimeException("Error converting YearJsonDto to Year: no model with id " + source.model_id());
        });

        Year year = new Year();
        year.setId(source.id());
        year.setName(source.name());
        year.setUrlPathName(source.url_path_name());
        year.setModel(model);
        return year;
    }
}
