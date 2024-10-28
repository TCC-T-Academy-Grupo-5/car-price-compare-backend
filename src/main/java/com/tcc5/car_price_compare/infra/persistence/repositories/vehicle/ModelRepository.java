package com.tcc5.car_price_compare.infra.persistence.repositories.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    Page<Model> findModelsByBrandId(UUID brandId, Pageable pageable);

    @Query(
            "SELECT new com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO(m.id, m.name) " +
                    "FROM Model m " +
                    "WHERE m.brand.id = :brandId"
    )
    List<OptionDTO> findOptionsByBrandId(UUID brandId);
}
