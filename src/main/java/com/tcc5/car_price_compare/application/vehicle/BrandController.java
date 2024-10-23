package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import com.tcc5.car_price_compare.shared.utils.PaginationHeaders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @GetMapping("/{slug}")
    public ResponseEntity<BrandDTO> getBrandByUrlPathName(@PathVariable String slug) {
        System.out.println("Slug recebido: " + slug);
        return ResponseEntity.status(HttpStatus.OK).body(service.findByUrlPathName(slug));
    }

    @GetMapping("/{brandId}/models")
    public ResponseEntity<List<ModelDTO>> getModelsByBrandId(@PathVariable UUID brandId,
            @RequestParam(required = false, value="pageNumber", defaultValue="1") Integer pageNumber,
            @RequestParam(required = false, value="pageSize", defaultValue="10") Integer pageSize) {
        pageNumber = Math.max(1, pageNumber);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<ModelDTO> models = service.findModelsByBrandId(brandId, pageable);
        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(models);

        return ResponseEntity.ok().headers(headers).body(models.getContent());
    }
}
