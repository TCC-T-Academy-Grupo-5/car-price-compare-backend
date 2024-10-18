package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.price.dto.FipePriceDTO;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.services.StorePriceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price")
public class PriceController {

    private final StorePriceService storePriceService;

    @Autowired
    public PriceController(StorePriceService storePriceService) {
        this.storePriceService = storePriceService;
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<FipePriceDTO> getPrice(
            @PathVariable String vehicleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(storePriceService.getPrice(vehicleId));
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<StorePriceDTO> getStorePrice(
            @PathVariable String id,
            @RequestParam(value = "price") String price
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(storePriceService.getStorePrice(id, price));
    }

    @PostMapping("/store")
    public ResponseEntity<StorePriceDTO> addStorePrice(@RequestBody @Valid StorePriceDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storePriceService.saveStorePrice(data));
    }
}
