package com.tcc5.car_price_compare.application.vehicle.brand;

import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddBrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.BrandFilterType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("brand")
@Tag(name = "Brands", description = "Endpoints for managing vehicle brands")
public class BrandController {

    private final BrandsResponseEntityService brandsResponseEntityService;
    private final BrandService service;

    public BrandController(BrandService service, BrandsResponseEntityService brandsResponseEntityService) {
        this.service = service;
        this.brandsResponseEntityService = brandsResponseEntityService;
    }

    @GetMapping
    @Operation(summary = "Get brands", description = "Retrieve a paginated list of brands based on filter type (popular, not popular, or all).")
    public ResponseEntity<List<BrandDTO>> getBrands(
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "vehicleType", required = false) Integer vehicleType,
            @RequestParam(value = "brandType", defaultValue = "ALL") BrandFilterType brandType
    ) {
        return brandsResponseEntityService.getBrands(pageNumber, pageSize, name, vehicleType, brandType);
    }

    @GetMapping("/{brandId}")
    @Operation(summary = "Get brand by ID", description = "Retrieves a brand by its ID.")
    public ResponseEntity<BrandDTO> getById(@PathVariable UUID brandId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(brandId));
    }

    @PostMapping
    @Operation(summary = "Add brand", description = "Adds a new brand.")
    public ResponseEntity<Brand> addBrand(@RequestBody @Valid AddBrandDTO brandDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(brandDto));
    }
}