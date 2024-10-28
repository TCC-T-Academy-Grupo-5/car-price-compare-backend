package com.tcc5.car_price_compare.application.vehicle.brand;

import com.tcc5.car_price_compare.application.ConversionService;
import com.tcc5.car_price_compare.application.vehicle.statistics.StatisticService;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddBrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.BrandNotFoundException;
import com.tcc5.car_price_compare.infra.persistence.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.infra.persistence.specifications.BrandSpecification;
import com.tcc5.car_price_compare.shared.utils.PaginationHeaders;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ConversionService conversionService;
    private final StatisticService statisticService;

    private static final List<String> popularBrands = List.of(
            "Aprilia", "Agrale", "Alfa Romeo", "Aston Martin", "Audi",
            "BMW",
            "Chevrolet", "Chery", "Chevrolet", "Citroën", "Chrysler",
            "Daewoo", "Drafa", "Ducati", "DAF", "Dodge",
            "Ferrari", "Fiat", "Ford",
            "Harley-Davidson", "Honda", "Hyundai",
            "International",
            "Jack", "Jaguar", "Jeep",
            "Kawasaki", "Kasinski", "Kia", "KTM",
            "Lamborghini", "Lexus", "Lifan",
            "Mack", "Mclaren", "MAN", "Mazda", "Mercedes-Benz", "Mitsubishi", "Moto Guzzi", "MV Agusta",
            "Nissan",
            "Porsche", "Peugeot",
            "RAM", "Renault", "Rolls-Royce", "Royal Enfield",
            "Scania", "Sinotruk", "Subaru", "Sundown", "Suzuki",
            "Toyota", "Triumph", "Troller",
            "UD Trucks",
            "Vespa", "Volkswagen", "Volvo",
            "Western Star",
            "Yamaha"
    );

    public BrandService(BrandRepository brandRepository, ConversionService conversionService, StatisticService statisticService) {
        this.brandRepository = brandRepository;
        this.conversionService = conversionService;
        this.statisticService = statisticService;
    }

    public Page<BrandDTO> findAll(String name, Integer type, Pageable pageable) {
        Specification<Brand> spec = Specification
                .where(BrandSpecification.hasBrand(name))
                .and(BrandSpecification.hasType(type));

        Page<Brand> pagedBrands = brandRepository.findAll(spec, pageable);
        List<BrandDTO> brandDTOs = convertToDTO(pagedBrands.getContent());
        if (name != null) { sendStatistic(name);}

        return new PageImpl<>(brandDTOs, pageable, pagedBrands.getTotalElements());
    }

    public Page<BrandDTO> findPopularBrands(Integer vehicleType, Pageable pageable) {
        List<Brand> popularBrandsFromDb = brandRepository.findPopularBrands(popularBrands);
        List<Brand> filteredPopularBrands = popularBrandsFromDb.stream()
                .filter(brand -> vehicleType == null || brand.getVehicleType().ordinal() == vehicleType)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredPopularBrands.size());
        List<BrandDTO> popularBrandDTOs = convertToDTO(filteredPopularBrands.subList(start, end));

        return new PageImpl<>(popularBrandDTOs, pageable, filteredPopularBrands.size());
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

    public ResponseEntity<List<BrandDTO>> getBrandsResponse(Integer page, Integer size, String name, Integer type, boolean isPopular) {
        page = Math.max(1, page);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BrandDTO> brands;
        brands = isPopular ? findPopularBrands(type, pageable) : findAll(name, type, pageable);
        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(brands);

        return ResponseEntity.ok().headers(headers)
                .contentType(MediaType.APPLICATION_JSON).body(brands.getContent());
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