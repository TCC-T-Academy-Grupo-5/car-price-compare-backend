package com.tcc5.car_price_compare.application.vehicle.brand;

import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.BrandFilterType;
import com.tcc5.car_price_compare.shared.utils.PaginationHeaders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandsResponseEntityService {

    final
    BrandService service;

    public BrandsResponseEntityService(BrandService service) {
        this.service = service;
    }

    public ResponseEntity<List<BrandDTO>> getBrands(Integer page, Integer size, String name, Integer type, BrandFilterType filterType) {
        page = Math.max(1, page);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BrandDTO> brands = service.findBrands(name, type, pageable, filterType);

        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(brands);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(brands.getContent());
    }
}
