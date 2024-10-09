package com.tcc5.car_price_compare.infra.fipedata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc5.car_price_compare.domain.vehicle.*;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.BrandNotFoundException;
import com.tcc5.car_price_compare.infra.fipedata.converter.*;
import com.tcc5.car_price_compare.infra.fipedata.dto.*;
import com.tcc5.car_price_compare.repositories.price.FipePriceRepository;
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
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class FipeDataImporter implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(FipeDataImporter.class);
    private final WebClient webClient = WebClient.builder().build();

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final YearRepository yearRepository;
    private final VehicleRepository vehicleRepository;
    private final FipePriceRepository fipePriceRepository;

    private final ObjectMapper objectMapper;

    private final BrandJsonDtoToBrandConverter brandJsonDtoToBrandConverter;
    private final ModelJsonDtoToModelConverter modelJsonDtoToModelConverter;
    private final YearJsonDtoToYearConverter yearJsonDtoToYearConverter;
    private final VehicleJsonDtoToVehicleConverter vehicleJsonDtoToVehicleConverter;
    private final FipePriceJsonDtoToFipePriceConverter fipePriceJsonDtoToFipePriceConverter;

    @Value("${fipe.data.import.baseurl}")
    private String baseUrl;

    @Value("${fipe.data.brands.numpages}")
    private int brandsNumPages;

    @Value("${fipe.data.models.numpages}")
    private int modelsNumPages;

    @Value("${fipe.data.years.numpages}")
    private int yearsNumPages;

    @Value("${fipe.data.vehicles.numpages}")
    private int vehiclesNumPages;

    @Value("${fipe.data.fipeprices.numpages}")
    private int fipePricesNumPages;

    public FipeDataImporter(BrandRepository brandRepository,
            ModelRepository modelRepository,
            YearRepository yearRepository,
            VehicleRepository vehicleRepository,
            ObjectMapper objectMapper,
            BrandJsonDtoToBrandConverter brandJsonDtoToBrandConverter,
            ModelJsonDtoToModelConverter modelJsonDtoToModelConverter,
            YearJsonDtoToYearConverter yearJsonDtoToYearConverter,
            VehicleJsonDtoToVehicleConverter vehicleJsonDtoToVehicleConverter,
            FipePriceRepository fipePriceRepository,
            FipePriceJsonDtoToFipePriceConverter fipePriceJsonDtoToFipePriceConverter) {
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.yearRepository = yearRepository;
        this.vehicleRepository = vehicleRepository;
        this.objectMapper = objectMapper;
        this.brandJsonDtoToBrandConverter = brandJsonDtoToBrandConverter;
        this.modelJsonDtoToModelConverter = modelJsonDtoToModelConverter;
        this.yearJsonDtoToYearConverter = yearJsonDtoToYearConverter;
        this.vehicleJsonDtoToVehicleConverter = vehicleJsonDtoToVehicleConverter;
        this.fipePriceRepository = fipePriceRepository;
        this.fipePriceJsonDtoToFipePriceConverter = fipePriceJsonDtoToFipePriceConverter;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.importBrands();
        this.importModels();
        this.importYears();
        this.importVehicles();
        this.importFipePrices();
    }

    private <T, U> void importToDatabase(int numPages,
            String entityName,
            Function<List<T>, List<U>> dtoToEntityconverterFunction,
            Consumer<List<U>> saveFunction,
            Class<T> dtoClass) {
        List<Mono<Void>> importTasks = new ArrayList<>();

        for (int i = 0; i < numPages; i++) {
            int pageNum = i + 1;
            String uri = this.baseUrl + "/" + entityName + "/" + entityName + "-" + pageNum + ".json";

            Mono<Void> importTask = this.webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(items -> {
                        try {
                            log.info("Importing " + entityName + ", page {}...", pageNum);

                            List<T> dtoList = this.objectMapper.readValue(items,
                                                                          this.objectMapper
                                                                                  .getTypeFactory()
                                                                                  .constructCollectionType(List.class, dtoClass));
                            List<U> entityList = dtoToEntityconverterFunction.apply(dtoList);

                            saveFunction.accept(entityList);
                            log.info(entityName + " imported successfully", pageNum);
                            return Mono.empty();
                        } catch (JsonProcessingException e) {
                            log.error("Error processing {}, page {}", entityName, pageNum, e);
                            return Mono.error(e);
                        }
                    })
                    .doOnError(error -> log.error("Error getting {}, page {}: {}", entityName, pageNum, error.getMessage()))
                    .then();

            importTasks.add(importTask);
        }

        Mono.when(importTasks).block();
    }

    private void importBrands() {
        if (this.brandRepository.count() != 0) {
            log.info("Skipping brands import. Already imported.");
            return;
        }

        Function<List<BrandJsonDto>, List<Brand>> brandConverter = brandJsonDtos -> brandJsonDtos.stream()
                .map(this.brandJsonDtoToBrandConverter::convert)
                .toList();
        Consumer<List<Brand>> saveBrands = this.brandRepository::saveAll;

        this.importToDatabase(this.brandsNumPages,
                              "brands",
                              brandConverter,
                              saveBrands,
                              BrandJsonDto.class);
    }

    private void importModels() {
        if (this.modelRepository.count() != 0) {
            log.info("Skipping models import. Already imported.");
            return;
        }

        Function<List<ModelJsonDto>, List<Model>> modelConverter = modelJsonDtos -> modelJsonDtos.stream()
                .map(this.modelJsonDtoToModelConverter::convert)
                .toList();
        Consumer<List<Model>> saveModels = this.modelRepository::saveAll;

        this.importToDatabase(this.modelsNumPages,
                              "models",
                              modelConverter,
                              saveModels,
                              ModelJsonDto.class);
    }

    public void importYears() {
        if (this.yearRepository.count() != 0) {
            log.info("Skipping years import. Already imported.");
            return;
        }

        Function<List<YearJsonDto>, List<Year>> yearConverter = yearJsonDtos -> yearJsonDtos.stream()
                .map(this.yearJsonDtoToYearConverter::convert)
                .toList();
        Consumer<List<Year>> saveYears = this.yearRepository::saveAll;

        this.importToDatabase(this.yearsNumPages,
                              "years",
                              yearConverter,
                              saveYears,
                              YearJsonDto.class);
    }

    public void importVehicles() {
        if (this.vehicleRepository.count() != 0) {
            log.info("Skipping vehicle import. Already imported.");
            return;
        }

        Function<List<VehicleJsonDto>, List<Vehicle>> vehicleConverter = vehicleJsonDtos -> vehicleJsonDtos.stream()
                .map(this.vehicleJsonDtoToVehicleConverter::convert)
                .toList();
        Consumer<List<Vehicle>> saveVehicles = this.vehicleRepository::saveAll;

        this.importToDatabase(this.vehiclesNumPages,
                              "versions",
                              vehicleConverter,
                              saveVehicles,
                              VehicleJsonDto.class);
    }

    public void importFipePrices() {
        if (this.fipePriceRepository.count() != 0) {
            log.info("Skipping fipe prices import. Already imported.");
            return;
        }

        Function<List<FipePriceJsonDto>, List<FipePrice>> fipePriceConverter = fipePriceJsonDtos -> fipePriceJsonDtos.stream()
                .map(this.fipePriceJsonDtoToFipePriceConverter::convert)
                .toList();
        Consumer<List<FipePrice>> saveFipePrices = this.fipePriceRepository::saveAll;

        this.importToDatabase(this.fipePricesNumPages,
                              "fipeprices",
                              fipePriceConverter,
                              saveFipePrices,
                              FipePriceJsonDto.class);
    }
}
