package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponseDTO;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.dto.*;
import com.tcc5.car_price_compare.shared.utils.PaginationHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("vehicle")
@Tag(name = "Vehicles", description = "Endpoints for managing vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all vehicles", description = "This endpoint retrieves a paginated list of all vehicles, optionally filtered by model, brand, fipe price, type and year.")
    public ResponseEntity<List<VehicleResponseDTO>> getVehicles(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "fipePrice", required = false) Double fipePrice,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "year", required = false) String year
    ) {
        pageNumber = Math.max(1, pageNumber);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<VehicleResponseDTO> vehicles = service.getVehicles(model, brand, fipePrice, type, year, pageable);
        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(vehicles);

        return ResponseEntity.ok().headers(headers).body(vehicles.getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID", description = "This endpoint retrieves a vehicle by its ID.")
    public ResponseEntity<VehicleResponseDTO> getVehicle(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getVehicleById(id));
    }

    @GetMapping("/{id}/deals")
    @Operation(summary = "Get vehicle store prices", description = "This endpoint retrieves a list of store prices for a vehicle.")
    public ResponseEntity<List<StorePriceDTO>> getVehicleStorePrices(@PathVariable UUID id) {
        return ResponseEntity.ok(this.service.getVehicleStorePrices(id));
    }

    @GetMapping("/brand")
    @Operation(summary = "Get all brands", description = "This endpoint retrieves a paginated list of all brands, optionally filtered by name and vehicle type.")
    public ResponseEntity<List<BrandDTO>> getBrands(
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "vehicleType", required = false) Integer type
    ){

        pageNumber = Math.max(1, pageNumber);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<BrandDTO> brands = service.getBrands(name, type, pageable);
        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(brands);

        return ResponseEntity.ok().headers(headers).body(brands.getContent());
    }

    @GetMapping("/model")
    @Operation(summary = "Get all models", description = "This endpoint retrieves a paginated list of all models, optionally filtered by name and brand.")
    public ResponseEntity<List<ModelDTO>> getModels(
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand
    ){
        pageNumber = Math.max(1, pageNumber);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<ModelDTO> models = service.getModels(name, brand, pageable);
        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(models);

        return ResponseEntity.ok().headers(headers).body(models.getContent());
    }

    @GetMapping("/year/{yearId}/options")
    @Operation(summary = "Get vehicle options by ID", description = "This endpoint retrieves a list of vehicle options by year ID.")
    public ResponseEntity<List<OptionDTO>> getVehicleOptionsByYearId(@PathVariable UUID yearId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getVehicleOptionsByYearId(yearId));
    }

    @PostMapping()
    @Operation(summary = "Add vehicle", description = "This endpoint adds a new vehicle.")
    public ResponseEntity<VehicleResponseDTO> addVehicle(@RequestBody @Valid AddVehicleDTO vehicleDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addVehicle(vehicleDTO));
    }

    @PostMapping("/brand")
    @Operation(summary = "Add brand", description = "This endpoint adds a new brand.")
    public ResponseEntity<Brand> addBrand(@RequestBody @Valid AddBrandDTO brandDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBrand(brandDTO));

    }

    @PostMapping("/model")
    @Operation(summary = "Add model", description = "This endpoint adds a new model.")
    public ResponseEntity<Model> addModel(@RequestBody @Valid AddModelDTO modelDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addModel(modelDTO));
    }
}

