package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.price.StorePrice;
import com.tcc5.car_price_compare.repositories.price.StorePriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class StorePriceService {

    private final StorePriceRepository storePriceRepository;

    public StorePriceService(StorePriceRepository storePriceRepository) {
        this.storePriceRepository = storePriceRepository;
    }

    public void saveStorePrices(List<StorePrice> storePrices) {
        this.storePriceRepository.saveAll(storePrices);
    }

    public List<StorePrice> getCurrentDayDeals(UUID vehicleId) {
        return this.storePriceRepository.findByScrapingDateAndVehicleId(LocalDate.now(), vehicleId);
    }
}
