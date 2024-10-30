package com.tcc5.car_price_compare.application.vehicle.brand;

import com.tcc5.car_price_compare.application.ConversionService;
import com.tcc5.car_price_compare.application.vehicle.statistics.StatisticService;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddBrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.BrandFilterType;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.BrandNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.infra.persistence.specifications.BrandSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ConversionService conversionService;
    private final StatisticService statisticService;

    private static final List<String> popularBrands = List.of(
            "Aprilia", "Audi",
            "BMW",
            "Chevrolet", "Chery", "Chevrolet", "Citroën",
            "Drafa", "Ducati", "Dodge",
            "Fiat", "Ford",
            "Harley-Davidson", "Honda", "Hyundai",
            "Jack", "Jaguar", "Jeep",
            "Kawasaki", "Kasinski", "Kia",
            "Lexus", "Lifan",
            "Mclaren", "MAN", "Mazda", "Mercedes-Benz", "Mitsubishi",
            "Nissan",
            "Peugeot",
            "RAM", "Renault",
            "Scania", "Subaru", "Suzuki",
            "Toyota", "Triumph",
            "Volkswagen", "Volvo",
            "Yamaha"
    );

    public BrandService(BrandRepository brandRepository, ConversionService conversionService, StatisticService statisticService) {
        this.brandRepository = brandRepository;
        this.conversionService = conversionService;
        this.statisticService = statisticService;
    }

    public Page<BrandDTO> findBrands(String name, Integer vehicleType, Pageable pageable, BrandFilterType brandType) {
        Specification<Brand> spec;

        switch (brandType) {
            case POPULAR -> spec = Specification.where(BrandSpecification.isPopular(popularBrands));
            case NOT_POPULAR -> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.not(root.get("name").in(popularBrands)));
            default -> spec = Specification.where(null);
        }

        spec = spec.and(BrandSpecification.hasBrand(name))
                .and(BrandSpecification.hasType(vehicleType));

        Page<Brand> pagedBrands = brandRepository.findAll(spec, pageable);
        return new PageImpl<>(convertToDTO(pagedBrands.getContent()), pageable, pagedBrands.getTotalElements());
    }

    public BrandDTO findById(UUID id) {
        var brand = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException(id));
        return conversionService.convertToBrandDTO(brand);
    }

    public Brand findByName(String name) {
        return brandRepository.findByName(name)
                .orElseThrow(() -> new BrandNotFoundException(name));
    }

    @Transactional
    public Brand add(AddBrandDTO brandDto) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandDto, brand);
        return brandRepository.save(brand);
    }

    private List<BrandDTO> convertToDTO(List<Brand> brands) {
        List<BrandDTO> brandsDto = new ArrayList<>();
        brands.forEach(brand -> brandsDto.add(conversionService.convertToBrandDTO(brand)));
        return brandsDto;
    }

    private void sendStatistic(String brand) {
        Optional<Brand> brandOpt = brandRepository.findByName(brand);
        brandOpt.ifPresent(value -> statisticService.incrementSearch(value.getId(), EntityType.BRAND));
    }
}
