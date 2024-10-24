package com.tcc5.car_price_compare.application.vehicle.brand;

import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddBrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.shared.utils.PaginationHeaders;
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
@RequestMapping("brand")
public class BrandController {

    private final BrandService service;

    public BrandController(BrandService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> getBrands(
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "vehicleType", required = false) Integer type
    ) {
        pageNumber = Math.max(1, pageNumber);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<BrandDTO> brands = service.findAll(name, type, pageable);
        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(brands);

        return ResponseEntity.ok().headers(headers).body(brands.getContent());
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandDTO> getById(@PathVariable UUID brandId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(brandId));
    }

    @PostMapping
    public ResponseEntity<Brand> addBrand(@RequestBody @Valid AddBrandDTO brandDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(brandDto));
    }
}