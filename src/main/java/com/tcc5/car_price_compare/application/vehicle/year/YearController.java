package com.tcc5.car_price_compare.application.vehicle.year;

import com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("year")
@Tag(name = "Years", description = "Endpoints for managing vehicle years")
public class YearController {

    private final YearService service;

    public YearController(YearService service) {
        this.service = service;
    }

    @GetMapping("/model/{modelId}/options")
    public ResponseEntity<List<OptionDTO>> getYearOptionsByModelId(@PathVariable UUID modelId) {
        return ResponseEntity.ok(service.findOptionsByModelId(modelId));
    }
}
