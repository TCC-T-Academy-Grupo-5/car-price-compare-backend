package com.tcc5.car_price_compare.application.vehicle.price;

import com.tcc5.car_price_compare.application.ConversionService;
import com.tcc5.car_price_compare.application.user.notification.NotificationService;
import com.tcc5.car_price_compare.domain.price.StorePrice;
import com.tcc5.car_price_compare.domain.price.dto.FipePriceDTO;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.domain.price.exceptions.PriceNotFoundException;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.VehicleNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.price.FipePriceRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.price.StorePriceRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class StorePriceService {

    private final StorePriceRepository storePriceRepository;
    private final ConversionService conversionService;
    private final FipePriceRepository fipePriceRepository;
    private final NotificationService notificationService;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public StorePriceService(StorePriceRepository storePriceRepository, ConversionService conversionService, FipePriceRepository fipePriceRepository, NotificationService notificationService, VehicleRepository vehicleRepository) {
        this.storePriceRepository = storePriceRepository;
        this.conversionService = conversionService;
        this.fipePriceRepository = fipePriceRepository;
        this.notificationService = notificationService;
        this.vehicleRepository = vehicleRepository;
    }

    public StorePriceDTO saveStorePrice(StorePriceDTO storePriceDTO) {
        StorePrice storePrice = conversionService.convertToStorePrice(storePriceDTO);

        sendNotification(storePriceDTO);

        return conversionService.convertToStorePriceDTO(storePriceRepository.save(storePrice));
    }

    public void saveStorePrices(List<StorePrice> storePrices) {
        this.storePriceRepository.saveAll(storePrices);
    }

    public List<StorePrice> getCurrentDayDeals(UUID vehicleId) {
        return this.storePriceRepository.findByScrapingDateAndVehicleId(LocalDate.now(), vehicleId);
    }

    public StorePriceDTO getStorePrice(String id, String price) {
        Double doublePrice = Double.parseDouble(price);
        var repositoryPrice = storePriceRepository.findByPriceEqualsAndVehicle_Id(doublePrice, UUID.fromString(id))
                .orElseThrow(() -> new PriceNotFoundException("Price not found for vehicle with id: " + id + " and price: " + price));

        return conversionService.convertToStorePriceDTO(repositoryPrice);
    }

    public FipePriceDTO getPrice(String vehicleId) {
        UUID id = UUID.fromString(vehicleId);

        var price = fipePriceRepository.findLatestPriceByVehicleId(id)
                .orElseThrow(() -> new PriceNotFoundException("No FIPE prices found for: " + id));

        return conversionService.convertToFipePriceDTO(price);
    }

    private void sendNotification(StorePriceDTO storePriceDTO) {
        try {
            FipePriceDTO fipePriceDTO = getPrice(String.valueOf(storePriceDTO.vehicleId()));

            if (storePriceDTO.price() < fipePriceDTO.price()) {
                var vehicle = vehicleRepository.findById(UUID.fromString(fipePriceDTO.vehicleId()))
                        .orElseThrow(() -> new VehicleNotFoundException(UUID.fromString(fipePriceDTO.vehicleId())));

                notificationService.sendVehicleNotifications(vehicle, fipePriceDTO.price());
            }
        } catch (PriceNotFoundException | VehicleNotFoundException e) {
            e.printStackTrace();
        }
    }
}
