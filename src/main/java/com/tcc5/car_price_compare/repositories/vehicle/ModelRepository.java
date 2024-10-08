package com.tcc5.car_price_compare.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ModelRepository extends CrudRepository<Model, UUID>, JpaSpecificationExecutor<Model> {
    Optional<Model> findByName(String name);

    @Query(
            "SELECT new com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO(m.id, m.name, m.urlPathName, m.imageUrl, m.brand.name) " +
                    "FROM Model m " +
                    "WHERE m.id = :id"
    )
    Optional<ModelDTO> findModelDTOById(@Param("id") UUID id);
}
