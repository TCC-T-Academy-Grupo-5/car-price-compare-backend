package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.application.ConversionService;
import com.tcc5.car_price_compare.application.vehicle.brand.BrandService;
import com.tcc5.car_price_compare.application.vehicle.model.ModelService;
import com.tcc5.car_price_compare.application.vehicle.price.StorePriceService;
import com.tcc5.car_price_compare.application.vehicle.statistics.StatisticService;
import com.tcc5.car_price_compare.domain.price.StorePrice;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.domain.price.dto.StorePricesRequestDTO;
import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponseDTO;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddBrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddVehicleDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.BrandNotFoundException;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.ModelNotFoundException;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.VehicleNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.price.FipePriceRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.ModelRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.VehicleRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.YearRepository;
import com.tcc5.car_price_compare.infra.persistence.specifications.VehicleSpecification;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FutureVehicleService {
    private static final Logger log = LoggerFactory.getLogger(FutureVehicleService.class);
    private final ConversionService conversionService;
    private final VehicleRepository vehicleRepository;
    private final YearRepository yearRepository;
    private final StatisticService statisticService;
    private final StorePriceService storePriceService;
    private final WebClient webClient;
    private final ModelService modelService;
    private final BrandService brandService;
    private String scrapingUrl;

    public FutureVehicleService(
            ConversionService conversionService,
            VehicleRepository vehicleRepository,
            WebClient webClient,
            YearRepository yearRepository,
            StatisticService statisticService,
            StorePriceService storePriceService,
            ModelService modelService,
            BrandService brandService
    ) {
        this.conversionService = conversionService;
        this.vehicleRepository = vehicleRepository;
        this.webClient = webClient;
        this.yearRepository = yearRepository;
        this.statisticService = statisticService;
        this.storePriceService = storePriceService;
        this.modelService = modelService;
        this.brandService = brandService;
    }

    public Page<VehicleResponseDTO> getAll(String model, String brand, Double fipePrice, Integer type, String year, Pageable pageable) {
        Specification<Vehicle> spec = Specification
                .where(VehicleSpecification.hasModel(model))
                .and(VehicleSpecification.hasBrand(brand))
                .and(VehicleSpecification.hasFipeMaxPrice(fipePrice))
                .and(VehicleSpecification.hasType(type))
                .and(VehicleSpecification.hasYear(year));

        Page<Vehicle> vehicles = vehicleRepository.findAll(spec, pageable);

        List<VehicleResponseDTO> vehicleResponses = vehicles.stream()
                .map(conversionService::convertToVehicleResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(vehicleResponses, pageable, vehicles.getTotalElements());
    }

    public VehicleResponseDTO getById(String id) {
        UUID uuid = UUID.fromString(id);
        var vehicle = vehicleRepository.findById(uuid);

        if (vehicle.isEmpty()) throw new VehicleNotFoundException(uuid);

        sendVehicleStatistic(id);

        return conversionService.convertToVehicleResponse(vehicle.get());
    }

    @Transactional
    public VehicleResponseDTO add(AddVehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();

        BeanUtils.copyProperties(vehicleDTO, vehicle);

        Model model = modelService.findByName(vehicleDTO.model());
        Brand brand = brandService.findByName(vehicleDTO.brand());
        Year year = yearConfig(vehicleDTO.year(), model);

        vehicle.setId(UUID.randomUUID());
        vehicle.setYear(year);
        vehicle.getYear().setModel(model);
        vehicle.getYear().getModel().setBrand(brand);
        vehicle.getYear().getModel().getBrand().setVehicleType(VehicleType.fromValue(vehicleDTO.carType()));

        addYear(year);

        vehicleRepository.save(vehicle);

        return conversionService.convertToVehicleResponse(vehicle);
    }


    // TODO: move Bellow to correct Class.
    public List<StorePriceDTO> getStorePrices(UUID vehicleId) {
        List<StorePrice> storePrices = this.storePriceService.getCurrentDayDeals(vehicleId);

        if (!storePrices.isEmpty()) {
            return storePrices.stream().map(this.conversionService::convertToStorePriceDTO).toList();
        }

        StorePricesRequestDTO storePriceRequest = this.vehicleRepository
                .createStorePricesRequestDtoFromVehicleId(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        List<StorePriceDTO> storePriceDTOS = this.webClient.post()
                .uri(this.scrapingUrl)
                .bodyValue(storePriceRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<StorePriceDTO>>() {
                })
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(throwable -> {
                    log.error("Error getting data from scraping microservice: {}", throwable.getMessage());
                    return Mono.just(List.of());
                })
                .block();

        if (storePriceDTOS != null && !storePriceDTOS.isEmpty()) {
            storePrices = storePriceDTOS.stream()
                    .map(this.conversionService::convertToStorePrice)
                    .toList();

            this.storePriceService.saveStorePrices(storePrices);
        }

        return storePriceDTOS;
    }

    private Year yearConfig(String yearString, Model model) {
        Year year = new Year();

        year.setId(UUID.randomUUID());
        year.setName(yearString);
        year.setModel(model);
        year.setUrlPathName(yearString.replace(" ", "-"));

        return year;
    }

    private void addYear(Year year) {
        yearRepository.save(year);
    }

    private List<BrandDTO> brandsListToBrandDTO(List<Brand> brands) {
        List<BrandDTO> brandsDto = new ArrayList<>();

        for (Brand b : brands) {
            brandsDto.add(conversionService.convertToBrandDTO(b));
        }

        return brandsDto;
    }

    private void sendVehicleStatistic(String id) {
        if (id != null && !(id.isEmpty() || id.isBlank())) {
            Optional<Vehicle> res = vehicleRepository.findById(UUID.fromString(id));

            res.ifPresent(value -> statisticService.incrementSearch(value.getId(), EntityType.VEHICLE));
        }
    }
}
