package com.tcc5.car_price_compare.domain.price.converters;

import com.tcc5.car_price_compare.domain.price.StorePrice;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.VehicleNotFoundException;
import com.tcc5.car_price_compare.repositories.vehicle.VehicleRepository;
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
        storePrice.setPrice(source.price());
        storePrice.setScrappingDate(source.scrapedAt());
        storePrice.setStore(source.store());
        storePrice.setMileageInKm(source.mileageInKm());
        storePrice.setImageUrl(source.imageUrl());
        storePrice.setDealUrl(source.dealUrl());
        storePrice.setVehicle(vehicle);

        return storePrice;
    }
}
