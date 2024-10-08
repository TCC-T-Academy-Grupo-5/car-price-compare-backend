package com.tcc5.car_price_compare.infra.fipedata.converter;

import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.infra.fipedata.dto.VehicleJsonDto;
import com.tcc5.car_price_compare.repositories.vehicle.YearRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VehicleJsonDtoToVehicleConverter implements Converter<VehicleJsonDto, Vehicle> {

    private final YearRepository yearRepository;

    public VehicleJsonDtoToVehicleConverter(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    @Override
    public Vehicle convert(VehicleJsonDto source) {
        Year year = this.yearRepository.findById(UUID.fromString(source.year_id())).orElseThrow(() -> {
            return new RuntimeException("Error converting VehicleJsonDto to Vehicle: no year with id " + source.year_id());
        });

        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.fromString(source.id()));
        vehicle.setFipeCode(source.fipe_code());
        vehicle.setUrlPathName(source.url_path_name());
        vehicle.setFullUrl(source.full_url());
        vehicle.setYear(year);
        return vehicle;
    }
}
