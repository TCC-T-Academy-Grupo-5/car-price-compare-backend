package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brand")
public class BrandController {

    private final BrandService service;
    public BrandController(BrandService service) {
        this.service = service;
    }

    @GetMapping("/{slug}")
    public ResponseEntity<BrandDTO> getBrandByUrlPathName(@PathVariable String slug) {
        System.out.println("Slug recebido: " + slug);
        return ResponseEntity.status(HttpStatus.OK).body(service.findByUrlPathName(slug));
    }
}
