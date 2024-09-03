package com.tcc5.car_price_compare.repositories;

import com.tcc5.car_price_compare.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
