package com.tcc5.car_price_compare.infra.persistence.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, UUID>, JpaSpecificationExecutor<Brand> {
    Optional<Brand> findByName(String name);

    @Query(
            "SELECT new com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO(b.id, b.name, b.urlPathName, b.imageUrl, b.vehicleType) " +
                    "FROM Brand b " +
                    "WHERE b.id = :id"
    )
    Optional<BrandDTO> findBrandDTOById(@Param("id") UUID id);

    Optional<Brand> findFirstByUrlPathName(@Param("slug") String urlPathName);
}