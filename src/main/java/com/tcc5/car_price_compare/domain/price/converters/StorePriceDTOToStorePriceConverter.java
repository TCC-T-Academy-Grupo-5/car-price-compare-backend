package com.tcc5.car_price_compare.domain.price.converters;

import com.tcc5.car_price_compare.domain.price.StorePrice;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.VehicleNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.VehicleRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StorePriceDTOToStorePriceConverter implements Converter<StorePriceDTO, StorePrice> {

    private final VehicleRepository vehicleRepository;

    public StorePriceDTOToStorePriceConverter(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public StorePrice convert(StorePriceDTO source) {
        Vehicle vehicle = this.vehicleRepository.findById(source.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(source.vehicleId()));

        StorePrice storePrice = new StorePrice();
        storePrice.setStore(source.store());
        storePrice.setPrice(source.price());
        storePrice.setMileageInKm(source.mileageInKm());
        storePrice.setModelName(source.modelName());
        storePrice.setVersionName(source.versionName());
        storePrice.setYear(source.year());
        storePrice.setDealUrl(source.dealUrl());
        storePrice.setImageUrl(source.imageUrl());
        storePrice.setIsFullMatch(source.isFullMatch());
        storePrice.setCity(source.city());
        storePrice.setState(source.state());
        storePrice.setScrappingDate(source.scrapedAt());
        storePrice.setVehicle(vehicle);

        return storePrice;
    }
}
