package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponse;
import com.tcc5.car_price_compare.services.VehicleService;
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
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        return ResponseEntity.status(HttpStatus.OK).body(service.getVehicles(pageNumber, pageSize));
    }
}

