package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponse;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.VehicleDTO;
import com.tcc5.car_price_compare.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vehicle")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> getVehicles(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "fipePrice", required = false) Double fipePrice,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "year", required = false) String year
            ){

        return ResponseEntity.status(HttpStatus.OK).body(service.getVehicles(pageNumber, pageSize, model, brand, fipePrice, type, year));
    }

    @PostMapping()
    public ResponseEntity<VehicleResponse> addVehicle(@RequestBody @Valid VehicleDTO vehicleDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addVehicle(vehicleDTO));
    }

    @PostMapping("/brand")
    public ResponseEntity<Brand> addBrand(@RequestBody @Valid BrandDTO brandDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBrand(brandDTO));

    }

    @PostMapping("/model")
    public ResponseEntity<Model> addModel(@RequestBody @Valid ModelDTO modelDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addModel(modelDTO));
    }
}

