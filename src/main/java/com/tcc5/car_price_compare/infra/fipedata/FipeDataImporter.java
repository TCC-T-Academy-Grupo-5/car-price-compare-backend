package com.tcc5.car_price_compare.infra.fipedata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc5.car_price_compare.infra.fipedata.dto.BrandJsonDTO;
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

import java.util.List;

@Component
public class FipeDataImporter implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(FipeDataImporter.class);
    private static final WebClient webClient = WebClient.builder().build();

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final YearRepository yearRepository;
    private final VehicleRepository vehicleRepository;
    private final ObjectMapper objectMapper;

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
            ObjectMapper objectMapper) {
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.yearRepository = yearRepository;
        this.vehicleRepository = vehicleRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.importBrands();
    }

    private void importBrands() {
        this.webClient.get()
                .uri(brandsUrl)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                        brands -> {
                            try {
                                List<BrandJsonDTO> brandsFromJson = this.objectMapper.readValue(brands, new TypeReference<>() {});
                                for (BrandJsonDTO brandJsonDTO : brandsFromJson) {
                                    System.out.println(brandJsonDTO);
                                }

                            } catch (JsonProcessingException e) {
                                log.error("Error importing brands: {}", e.getMessage());
                            }
                        },

                        error -> log.error("Error getting brands: {}", error.getMessage())
                );
    }
}
