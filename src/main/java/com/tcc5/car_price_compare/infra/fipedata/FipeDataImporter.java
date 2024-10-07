package com.tcc5.car_price_compare.infra.fipedata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.infra.fipedata.converter.BrandJsonDtoToBrandConverter;
import com.tcc5.car_price_compare.infra.fipedata.converter.ModelJsonDtoToModelConverter;
import com.tcc5.car_price_compare.infra.fipedata.converter.VehicleJsonDtoToVehicleConverter;
import com.tcc5.car_price_compare.infra.fipedata.converter.YearJsonDtoToYearConverter;
import com.tcc5.car_price_compare.infra.fipedata.dto.BrandJsonDto;
import com.tcc5.car_price_compare.infra.fipedata.dto.ModelJsonDto;
import com.tcc5.car_price_compare.infra.fipedata.dto.VehicleJsonDto;
import com.tcc5.car_price_compare.infra.fipedata.dto.YearJsonDto;
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
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
    private final ModelJsonDtoToModelConverter modelJsonDtoToModelConverter;
    private final YearJsonDtoToYearConverter yearJsonDtoToYearConverter;
    private final VehicleJsonDtoToVehicleConverter vehicleJsonDtoToVehicleConverter;

    @Value("${fipe.data.brands.baseurl}")
    private String brandsUrl;

    @Value("${fipe.data.brands.numpages}")
    private int brandsNumPages;

    @Value("${fipe.data.models.baseurl}")
    private String modelsUrl;

    @Value("${fipe.data.models.numpages}")
    private int modelsNumPages;

    @Value("${fipe.data.years.baseurl}")
    private String yearsUrl;

    @Value("${fipe.data.years.numpages}")
    private int yearsNumPages;

    @Value("${fipe.data.vehicles.baseurl}")
    private String vehiclesUrl;

    @Value("${fipe.data.vehicles.numpages}")
    private int vehiclesNumPages;

    public FipeDataImporter(BrandRepository brandRepository,
            ModelRepository modelRepository,
            YearRepository yearRepository,
            VehicleRepository vehicleRepository,
            ObjectMapper objectMapper,
            BrandJsonDtoToBrandConverter brandJsonDtoToBrandConverter,
            ModelJsonDtoToModelConverter modelJsonDtoToModelConverter,
            YearJsonDtoToYearConverter yearJsonDtoToYearConverter,
            VehicleJsonDtoToVehicleConverter vehicleJsonDtoToVehicleConverter) {
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.yearRepository = yearRepository;
        this.vehicleRepository = vehicleRepository;
        this.objectMapper = objectMapper;
        this.brandJsonDtoToBrandConverter = brandJsonDtoToBrandConverter;
        this.modelJsonDtoToModelConverter = modelJsonDtoToModelConverter;
        this.yearJsonDtoToYearConverter = yearJsonDtoToYearConverter;
        this.vehicleJsonDtoToVehicleConverter = vehicleJsonDtoToVehicleConverter;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.importBrands();
        this.importModels();
        this.importYears();
        this.importVehicles();

        // Refactor

        // import brands
        this.importToDatabase(this.brandsNumPages, this.brandsUrl, "brands");
    }

    private <T, U> void importToDatabase(int numPages, String partialUrl, String entityName, Function<T, U>) {
        List<Mono<Void>> importTasks = new ArrayList<>();

        for (int i = 0; i < numPages; i++) {
            int pageNum = i + 1;
            String uri = partialUrl + "/" + entityName + "-" + pageNum + ".json";

            Mono<Void> importTask = this.webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(items -> {
                        try {
                            log.info("Importing " + entityName + ", page {}...", pageNum);

                            List<T> dtoList = this.objectMapper.readValue(items, new TypeReference<>(){});
                            List<U> entityList = dtoList.stream()
                                    .map()
                        } catch (JsonProcessingException e) {

                        }
                    })
        }
    }

    private void importBrands() {
        if (this.brandRepository.count() != 0) {
            log.info("Skipping brands import. Already imported.");
            return;
        }

        List<Mono<Void>> importTasks = new ArrayList<>();

        for (int i = 0; i < brandsNumPages; i++) {
            int pageNum = i + 1;
            String uri = this.brandsUrl + "/brand-" + pageNum + ".json";

            Mono<Void> importTask = this.webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(brands -> {
                        try {
                            log.info("Importing brands, page {} ...", pageNum);

                            List<BrandJsonDto> brandsFromJson = this.objectMapper.readValue(brands, new TypeReference<>() {
                            });
                            List<Brand> brandsToSave = brandsFromJson.stream()
                                    .map(this.brandJsonDtoToBrandConverter::convert)
                                    .toList();

                            this.brandRepository.saveAll(brandsToSave);
                            log.info("Brands page {} imported to database successfully", pageNum);
                            return Mono.empty();
                        } catch (JsonProcessingException e) {
                            log.error("Error importing brands: {}, page: {}", e.getMessage(), pageNum);
                            return Mono.error(e);

                        }
                    })
                    .doOnError(error -> log.error("Error getting brands: {}, page: {}", error.getMessage(), pageNum))
                    .then();

            importTasks.add(importTask);
        }

        Mono.when(importTasks).block();
    }

    private void importModels() {
        if (this.modelRepository.count() != 0) {
            log.info("Skipping models import. Already imported.");
            return;
        }

        List<Mono<Void>> importTasks = new ArrayList<>();

        for (int i = 0; i < modelsNumPages; i++) {
            int pageNum = i + 1;
            String uri = this.modelsUrl + "/model-" + pageNum + ".json";

            Mono<Void> importTask = this.webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .publishOn(Schedulers.boundedElastic())
                    .flatMap(models -> {
                        try {
                            log.info("Importing models, page {}...", pageNum);

                            List<ModelJsonDto> modelsFromJson = this.objectMapper.readValue(models, new TypeReference<>() {});
                            List<Model> modelsToSave = modelsFromJson.stream()
                                    .map(this.modelJsonDtoToModelConverter::convert)
                                    .toList();

                            this.modelRepository.saveAll(modelsToSave);
                            log.info("Models page {} imported to database successfully", pageNum);
                            return Mono.empty();
                        } catch (JsonProcessingException e) {
                            log.error("Error importing models: {}, page: {}", e.getMessage(), pageNum);
                            return Mono.error(e);
                        }
                    })
                    .doOnError(error -> log.error("Error getting models: {}, page: {}", error.getMessage(), pageNum))
                    .then();

            importTasks.add(importTask);
        }

        Mono.when(importTasks).block();
    }

    public void importYears() {
        if (this.yearRepository.count() != 0) {
            log.info("Skipping years import. Already imported.");
            return;
        }

        List<Mono<Void>> importTasks = new ArrayList<>();

        for (int i = 0; i < yearsNumPages; i++) {
            int pageNum = i + 1;
            String uri = this.yearsUrl + "/year-" + pageNum + ".json";

            Mono<Void> importTask = this.webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(years -> {
                        try {
                            log.info("Importing years, page {}...", pageNum);

                            List<YearJsonDto> yearsFromJson = this.objectMapper.readValue(years, new TypeReference<>() {});
                            List<Year> yearsToSave = yearsFromJson.stream()
                                    .map(this.yearJsonDtoToYearConverter::convert)
                                    .toList();

                            this.yearRepository.saveAll(yearsToSave);
                            log.info("Years page {} imported to database successfully", pageNum);
                            return Mono.empty();
                        } catch (JsonProcessingException e) {
                            log.error("Error importing years: {}, page: {}", e.getMessage(), pageNum);
                            return Mono.error(e);

                        }
                    })
                    .doOnError(error -> log.error("Error getting years: {}, page: {}", error.getMessage(), pageNum))
                    .then();

            importTasks.add(importTask);
        }

        Mono.when(importTasks).block();
    }

    public void importVehicles() {
        if (this.vehicleRepository.count() != 0) {
            log.info("Skipping vehicles import. Already imported.");
            return;
        }

        List<Mono<Void>> importTasks = new ArrayList<>();

        for (int i = 0; i < vehiclesNumPages; i++) {
            int pageNum = i + 1;
            String uri = this.vehiclesUrl + "/vehicle-" + pageNum + ".json";

            Mono<Void> imporTask = this.webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(vehicles -> {
                        try {
                            log.info("Importing vehicles, page {}...", pageNum);

                            List<VehicleJsonDto> vehiclesFromJson = this.objectMapper.readValue(vehicles, new TypeReference<>() {});
                            List<Vehicle> vehiclesToSave = vehiclesFromJson.stream()
                                    .map(this.vehicleJsonDtoToVehicleConverter::convert)
                                    .toList();

                            this.vehicleRepository.saveAll(vehiclesToSave);
                            log.info("Vehicles page {} imported to database successfully", pageNum);
                            return Mono.empty();
                        } catch (JsonProcessingException e) {
                            log.error("Error importing vehicles: {}, page: {}", e.getMessage(), pageNum);
                            return Mono.error(e);
                        }
                    })
                    .doOnError(error -> log.error("Error getting vehicles: {}, page: {}", error.getMessage(), pageNum))
                    .then();

            importTasks.add(imporTask);
        }

        Mono.when(importTasks).block();
    }
}
