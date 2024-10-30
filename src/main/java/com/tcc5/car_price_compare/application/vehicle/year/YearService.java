package com.tcc5.car_price_compare.application.vehicle.year;

import com.tcc5.car_price_compare.domain.vehicle.dto.OptionDTO;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.YearRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class YearService {

    private final YearRepository repository;

    public YearService(YearRepository repository) {
        this.repository = repository;
    }

    public List<OptionDTO> findOptionsByModelId(UUID modelId) {
        return repository.findOptionsByModelId(modelId);
    }
}
