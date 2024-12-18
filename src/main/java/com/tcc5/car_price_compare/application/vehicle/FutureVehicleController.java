package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponseDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddVehicleDTO;
import com.tcc5.car_price_compare.shared.utils.PaginationHeaders;
import io.swagger.v3.oas.annotations.Hidden;
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
@RequestMapping("car")
@Hidden
public class FutureVehicleController {
    private final VehicleService service;

    public FutureVehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
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
    public ResponseEntity<VehicleResponseDTO> getVehicle(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getVehicleById(id));
    }

    @GetMapping("/{id}/deals")
    public ResponseEntity<List<StorePriceDTO>> getVehicleStorePrices(@PathVariable UUID id) {
        return ResponseEntity.ok(this.service.getVehicleStorePrices(id));
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> addVehicle(@RequestBody @Valid AddVehicleDTO vehicleDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addVehicle(vehicleDTO));
    }
}
