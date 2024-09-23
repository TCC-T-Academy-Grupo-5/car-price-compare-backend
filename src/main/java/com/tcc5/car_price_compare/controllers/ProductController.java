package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.product.Product;
import com.tcc5.car_price_compare.domain.product.ProductRequestDTO;
import com.tcc5.car_price_compare.domain.product.ProductResponseDTO;
import com.tcc5.car_price_compare.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @PostMapping
    public ResponseEntity postProduct(@RequestBody @Valid ProductRequestDTO body){
        Product newProduct = new Product(body);

        this.repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getAllProducts(){
        List<ProductResponseDTO> productList = this.repository.findAll().stream().map(ProductResponseDTO::new).toList();

        return ResponseEntity.ok(productList);
    }
}