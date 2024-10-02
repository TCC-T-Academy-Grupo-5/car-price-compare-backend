package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.price.FipePrice;
import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponseDTO;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.domain.vehicle.dto.*;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.BrandNotFoundException;
import com.tcc5.car_price_compare.domain.vehicle.exceptions.ModelNotFoundException;
import com.tcc5.car_price_compare.repositories.price.FipePriceRepository;
import com.tcc5.car_price_compare.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.repositories.vehicle.ModelRepository;
import com.tcc5.car_price_compare.repositories.vehicle.VehicleRepository;
import com.tcc5.car_price_compare.repositories.vehicle.YearRepository;
import com.tcc5.car_price_compare.specifications.VehicleSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    //TODO: add price into addVehicle

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private FipePriceRepository fipePriceRepository;

    public Page<VehicleResponseDTO> getVehicles(Integer pageNumber, Integer pageSize, String model, String brand, Double fipePrice, Integer type, String year) {
        Specification<Vehicle> spec = Specification
                .where(VehicleSpecification.hasModel(model))
                .and(VehicleSpecification.hasBrand(brand))
                .and(VehicleSpecification.hasFipeMaxPrice(fipePrice))
                .and(VehicleSpecification.hasType(type))
                .and(VehicleSpecification.hasYear(year));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);


        Page<Vehicle> vehicles = vehicleRepository.findAll(spec, pageable);

        return vehicleToList(vehicles);
    }

    private Page<VehicleResponseDTO> vehicleToList(Page<Vehicle> vehicles){
        List<VehicleResponseDTO> vehicleResponses = vehicles.stream()
                .map(this::convertToVehicleResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(vehicleResponses, PageRequest.of(vehicles.getNumber(), vehicles.getSize()), vehicles.getTotalElements());
    }

    private VehicleResponseDTO convertToVehicleResponse(Vehicle vehicle) {
        Year year = vehicle.getYear();
        Model model = year.getModel();
        Brand brand = model.getBrand();
        VehicleType vehicleType = brand.getVehicleType();
        FipePrice fipePrice = vehicle.getFipePrice();

        return new VehicleResponseDTO(vehicle.getId(), model.getName(), brand.getName(), fipePrice == null ? 0 : fipePrice.getPrice(), vehicleType.name(), year.getName().split(" ")[0]);
    }

    public Brand addBrand(AddBrandDTO brandDTO) {
        Brand brand = new Brand();

        BeanUtils.copyProperties(brandDTO, brand);

        return brandRepository.save(brand);
    }

    public Model addModel(AddModelDTO modelDTO) {
        Model model = new Model();

        BeanUtils.copyProperties(modelDTO, model);

        var brand = brandRepository.findByName(modelDTO.brandName());

        if (brand.isPresent())
            return modelRepository.save(model);
        else
            throw new RuntimeException("Brand not found");
    }


    public VehicleResponseDTO addVehicle(AddVehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();

        BeanUtils.copyProperties(vehicleDTO, vehicle);

        Model model = getModelByName(vehicleDTO.model());
        Brand brand = getBrandByName(vehicleDTO.brand());
        Year year = yearConfig(vehicleDTO.year(), vehicle, model);

        vehicle.setId(UUID.randomUUID());
        vehicle.setYear(year);
        vehicle.getYear().setModel(model);
        vehicle.getYear().getModel().setBrand(brand);
        vehicle.getYear().getModel().getBrand().setVehicleType(VehicleType.fromValue(vehicleDTO.carType()));

        addYear(year);

        vehicleRepository.save(vehicle);

        return convertToVehicleResponse(vehicle);
    }

    public Brand getBrandByName(String name){
        var brand = brandRepository.findByName(name);

        if (brand.isEmpty()) throw new BrandNotFoundException(name);

        return brand.get();
    }

    public Model getModelByName(String name){
        var model = modelRepository.findByName(name);

        if (model.isEmpty()) throw new ModelNotFoundException(name);

        return model.get();
    }

    private Year yearConfig(String yearString, Vehicle vehicle, Model model){
        Year year = new Year();

        year.setId(UUID.randomUUID());
        year.setName(yearString);
        year.setVehicle(vehicle);
        year.setModel(model);
        year.setUrlPathName(yearString.replace(" ", "-"));

        return year;
    }

    private void addYear(Year year) {
        yearRepository.save(year);
    }
}
