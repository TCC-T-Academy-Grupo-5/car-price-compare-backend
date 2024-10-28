package com.tcc5.car_price_compare.application.vehicle.model;

import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO;
import com.tcc5.car_price_compare.shared.utils.PaginationHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("model")
@Tag(name = "Models", description = "Endpoints for managing vehicle models")
public class ModelController {

    private final ModelService service;

    public ModelController(ModelService service) {
        this.service = service;
    }

    @GetMapping("/brand/{brandId}")
    @Operation(summary = "Get models by brand ID", description = "This endpoint retrieves a paginated list of models by brand ID.")
    public ResponseEntity<List<ModelDTO>> getModelsByBrandId(
            @PathVariable UUID brandId,
            @RequestParam(required = false, value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        pageNumber = Math.max(1, pageNumber);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<ModelDTO> models = service.findModelsByBrandId(brandId, pageable);
        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(models);

        return ResponseEntity.ok().headers(headers).body(models.getContent());
    }

    @GetMapping
    @Operation(summary = "Get all models", description = "This endpoint retrieves a paginated list of all models, optionally filtered by name and brand.")
    public ResponseEntity<List<ModelDTO>> getModels(
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand
    ){
        pageNumber = Math.max(1, pageNumber);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<ModelDTO> models = service.findAll(name, brand, pageable);
        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(models);

        return ResponseEntity.ok().headers(headers).body(models.getContent());
    }

    @GetMapping("/brand/{brandId}/options")
    @Operation(summary = "Get model options by brand ID", description = "This endpoint retrieves a list of model options by brand ID.")
    public ResponseEntity<List<OptionDTO>> getModelOptionsByBrandId(@PathVariable UUID brandId){
        return ResponseEntity.status(HttpStatus.OK).body(service.findOptionsByBrandId(brandId));
    }

    @PostMapping
    @Operation(summary = "Add model", description = "This endpoint adds a new model.")
    public ResponseEntity<Model> addModel(@RequestBody @Valid AddModelDTO modelDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(modelDTO));
    }
}