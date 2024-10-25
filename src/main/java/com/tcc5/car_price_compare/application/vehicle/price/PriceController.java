package com.tcc5.car_price_compare.application.vehicle.price;

import com.tcc5.car_price_compare.domain.price.dto.FipePriceDTO;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price")
@Tag(name = "Price", description = "Endpoints for managing vehicle prices")
public class PriceController {

    private final StorePriceService storePriceService;

    @Autowired
    public PriceController(StorePriceService storePriceService) {
        this.storePriceService = storePriceService;
    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(summary = "Get vehicle price", description = "This endpoint retrieves the price of a vehicle by its ID.")
    public ResponseEntity<FipePriceDTO> getPrice(
            @PathVariable String vehicleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(storePriceService.getPrice(vehicleId));
    }

    @GetMapping("/store/{id}")
    @Operation(summary = "Get store price", description = "This endpoint retrieves the price of a vehicle in a store by its ID.")
    public ResponseEntity<StorePriceDTO> getStorePrice(
            @PathVariable String id,
            @RequestParam(value = "price") String price
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(storePriceService.getStorePrice(id, price));
    }

    @PostMapping("/store")
    @Operation(summary = "Add store price", description = "This endpoint adds a new store price.")
    public ResponseEntity<StorePriceDTO> addStorePrice(@RequestBody @Valid StorePriceDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storePriceService.saveStorePrice(data));
    }
}
