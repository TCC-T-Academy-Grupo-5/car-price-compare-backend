package com.tcc5.car_price_compare.application.vehicle.statistics;

import com.tcc5.car_price_compare.domain.response.statistic.StatisticResponseDto;
import com.tcc5.car_price_compare.domain.response.statistic.StatisticVehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic")
@Tag(name = "Statistics", description = "Endpoints for vehicle statistics")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/vehicle")
    @Operation(summary = "Get vehicle statistics", description = "This endpoint retrieves statistics about vehicles.")
    public ResponseEntity<List<StatisticVehicleResponse>> getVehicleStats() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.getVehicles());
    }


    @GetMapping("/brand")
    @Operation(summary = "Get brand statistics", description = "This endpoint retrieves statistics about brands.")
    public ResponseEntity<List<StatisticResponseDto>> getBrandStats() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.getBrands());
    }


    @GetMapping("/model")
    @Operation(summary = "Get model statistics", description = "This endpoint retrieves statistics about models.")
    public ResponseEntity<List<StatisticResponseDto>> getModelStats() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.getModels());
    }

    @GetMapping("/year")
    @Operation(summary = "Get year statistics", description = "This endpoint retrieves statistics about years.")
    public ResponseEntity<List<StatisticResponseDto>> getYears(){
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.getYears());
    }
}
