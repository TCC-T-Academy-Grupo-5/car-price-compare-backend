package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.application.user.ConversionService;
import com.tcc5.car_price_compare.domain.price.StorePrice;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.domain.price.dto.StorePricesRequestDTO;
import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponseDTO;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.domain.vehicle.dto.*;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.BrandNotFoundException;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.ModelNotFoundException;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.VehicleNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.price.FipePriceRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.ModelRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.VehicleRepository;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.YearRepository;
import com.tcc5.car_price_compare.infra.persistence.specifications.BrandSpecification;
import com.tcc5.car_price_compare.infra.persistence.specifications.ModelSpecification;
import com.tcc5.car_price_compare.infra.persistence.specifications.VehicleSpecification;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
public class VehicleService {
    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

    //TODO: add price into addVehicle

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private FipePriceRepository fipePriceRepository;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private StorePriceService storePriceService;

    @Autowired
    private WebClient webClient;

    @Value("${storepricescraping.url}")
    private String scrapingUrl;

    public Page<VehicleResponseDTO> getVehicles(String model, String brand, Double fipePrice, Integer type, String year, Pageable pageable) {
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

    public VehicleResponseDTO getVehicleById(String id) {
        UUID uuid = UUID.fromString(id);
        var vehicle = vehicleRepository.findById(uuid);

        if (vehicle.isEmpty()) throw new VehicleNotFoundException(uuid);

        sendVehicleStatistic(id);

        return conversionService.convertToVehicleResponse(vehicle.get());
    }

    public List<StorePriceDTO> getVehicleStorePrices(UUID vehicleId) {
        List<StorePrice> storePrices = this.storePriceService.getCurrentDayDeals(vehicleId);

        if (!storePrices.isEmpty()) {
            return storePrices.stream().map(deal -> this.conversionService.convertToStorePriceDTO(deal)).toList();
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
                    .map(storePrice -> this.conversionService.convertToStorePrice(storePrice))
                    .toList();

            this.storePriceService.saveStorePrices(storePrices);
        }

        return storePriceDTOS;
    }


    private Page<VehicleResponseDTO> vehicleToList(Page<Vehicle> vehicles) {
        List<VehicleResponseDTO> vehicleResponses = vehicles.stream()
                .map(v -> conversionService.convertToVehicleResponse(v))
                .collect(Collectors.toList());

        return new PageImpl<>(vehicleResponses, PageRequest.of(vehicles.getNumber(), vehicles.getSize()), vehicles.getTotalElements());
    }

    @Transactional
    public Brand addBrand(AddBrandDTO brandDTO) {
        Brand brand = new Brand();

        BeanUtils.copyProperties(brandDTO, brand);

        return brandRepository.save(brand);
    }

    @Transactional
    public Model addModel(AddModelDTO modelDTO) {
        Model model = new Model();

        BeanUtils.copyProperties(modelDTO, model);

        var brand = brandRepository.findByName(modelDTO.brandName());

        if (brand.isPresent())
            return modelRepository.save(model);
        else
            throw new RuntimeException("Brand not found");
    }

    @Transactional
    public VehicleResponseDTO addVehicle(AddVehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();

        BeanUtils.copyProperties(vehicleDTO, vehicle);

        Model model = getModelByName(vehicleDTO.model());
        Brand brand = getBrandByName(vehicleDTO.brand());
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

    public Brand getBrandByName(String name) {
        var brand = brandRepository.findByName(name);

        if (brand.isEmpty()) throw new BrandNotFoundException(name);

        return brand.get();
    }

    public Page<BrandDTO> getBrands(String name, Integer type, Pageable pageable) {
        Specification<Brand> spec = Specification
                .where(BrandSpecification.hasBrand(name))
                .and(BrandSpecification.hasType(type));

        Page<Brand> pagedBrands = brandRepository.findAll(spec, pageable);

        List<BrandDTO> brandDTOs = brandsListToBrandDTO(pagedBrands.getContent());

        sendBrandStatistic(name);

        return new PageImpl<>(brandDTOs, PageRequest.of(pagedBrands.getNumber(), pagedBrands.getSize()), pagedBrands.getTotalElements());
    }

    public Model getModelByName(String name) {
        var model = modelRepository.findByName(name);

        if (model.isEmpty()) throw new ModelNotFoundException(name);

        return model.get();
    }

    public Page<ModelDTO> getModels(String name, String brand, Pageable pageable) {
        Specification<Model> spec = Specification
                .where(ModelSpecification.hasModel(name))
                .and(ModelSpecification.hasBrand(brand));

        Page<Model> pagedModels = modelRepository.findAll(spec, pageable);

        List<ModelDTO> modelDTOs = modelsListToModelDTO(pagedModels.getContent());

        return new PageImpl<>(modelDTOs, pageable, pagedModels.getTotalElements());
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

    private List<ModelDTO> modelsListToModelDTO(List<Model> models) {
        List<ModelDTO> modelsDto = new ArrayList<>();

        for (Model m : models) {
            modelsDto.add(conversionService.convertToModelDTO(m));
        }

        return modelsDto;
    }

    private void sendModelStatistics(String model) {
        if (model != null && !(model.isEmpty() || model.isBlank())) {
            Optional<Model> res = modelRepository.findByName(model);

            res.ifPresent(value -> statisticService.incrementSearch(value.getId(), EntityType.MODEL));
        }
    }

    private void sendBrandStatistic(String brand) {
        if (brand != null && !(brand.isEmpty() || brand.isBlank())) {
            Optional<Brand> res = brandRepository.findByName(brand);

            res.ifPresent(value -> statisticService.incrementSearch(value.getId(), EntityType.BRAND));
        }
    }

    private void sendVehicleStatistic(String id) {
        if (id != null && !(id.isEmpty() || id.isBlank())) {
            Optional<Vehicle> res = vehicleRepository.findById(UUID.fromString(id));

            res.ifPresent(value -> statisticService.incrementSearch(value.getId(), EntityType.VEHICLE));
        }
    }
}
