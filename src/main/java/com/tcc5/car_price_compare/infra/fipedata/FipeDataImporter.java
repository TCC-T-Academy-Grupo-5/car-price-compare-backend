package com.tcc5.car_price_compare.infra.fipedata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.infra.fipedata.converter.BrandJsonDtoToBrandConverter;
import com.tcc5.car_price_compare.infra.fipedata.dto.BrandJsonDto;
import com.tcc5.car_price_compare.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.repositories.vehicle.ModelRepository;
import com.tcc5.car_price_compare.repositories.vehicle.VehicleRepository;
import com.tcc5.car_price_compare.repositories.vehicle.YearRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class FipeDataImporter implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(FipeDataImporter.class);
    private final WebClient webClient = WebClient.builder().build();

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final YearRepository yearRepository;
    private final VehicleRepository vehicleRepository;

    private final ObjectMapper objectMapper;

    private final BrandJsonDtoToBrandConverter brandJsonDtoToBrandConverter;

    @Value("${fipe.data.brands.url}")
    private String brandsUrl;

    @Value("${fipe.data.models.url}")
    private String modelsUrl;

    @Value("${fipe.data.years.url}")
    private String yearsUrl;

    @Value("${fipe.data.vehicles.url}")
    private String vehiclesUrl;

    public FipeDataImporter(BrandRepository brandRepository,
            ModelRepository modelRepository,
            YearRepository yearRepository,
            VehicleRepository vehicleRepository,
            ObjectMapper objectMapper,
            BrandJsonDtoToBrandConverter brandJsonDtoToBrandConverter) {
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.yearRepository = yearRepository;
        this.vehicleRepository = vehicleRepository;
        this.objectMapper = objectMapper;
        this.brandJsonDtoToBrandConverter = brandJsonDtoToBrandConverter;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.importBrands();
    }

    private void importBrands() {
        if (this.brandRepository.count() == 0) {
            return;
        }

        this.webClient.get()
                .uri(brandsUrl)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                        brands -> {
                            try {
                                log.info("Importing brands...");

                                List<BrandJsonDto> brandsFromJson = this.objectMapper.readValue(brands, new TypeReference<>() {});
                                List<Brand> brandsToSave = brandsFromJson.stream()
                                        .map(this.brandJsonDtoToBrandConverter::convert)
                                        .toList();

                                this.brandRepository.saveAll(brandsToSave);
                                log.info("Brands imported to database successfully");
                            } catch (JsonProcessingException e) {
                                log.error("Error importing brands: {}", e.getMessage());
                            }
                        },

                        error -> log.error("Error getting brands: {}", error.getMessage())
                );
    }
}
