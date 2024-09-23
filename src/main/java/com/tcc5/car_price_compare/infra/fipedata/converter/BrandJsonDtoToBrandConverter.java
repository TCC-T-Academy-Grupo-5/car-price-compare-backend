package com.tcc5.car_price_compare.infra.fipedata.converter;

import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import com.tcc5.car_price_compare.infra.fipedata.dto.BrandJsonDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BrandJsonDtoToBrandConverter implements Converter<BrandJsonDto, Brand> {
    @Override
    public Brand convert(BrandJsonDto source) {
        Brand brand = new Brand();
        brand.setId(source.id());
        brand.setName(source.name());
        brand.setUrlPathName(source.url_path_name());
        brand.setVehicleType(VehicleType.fromValue(source.vehicle_type()));
        return brand;
    }
}
