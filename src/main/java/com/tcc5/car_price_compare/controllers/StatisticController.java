package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.response.statistic.StatisticResponseDto;
import com.tcc5.car_price_compare.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/vehicle")
    public ResponseEntity<List<StatisticResponseDto>> getVehicleStats() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.getVehicles());
    }


    @GetMapping("/brand")
    public ResponseEntity<List<StatisticResponseDto>> getBrandStats() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.getBrands());
    }


    @GetMapping("/model")
    public ResponseEntity<List<StatisticResponseDto>> getModelStats() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.getModels());

    }
}
