package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.price.FipePrice;
import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponse;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.domain.vehicle.dto.AddVehicleDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
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

    public Page<VehicleResponse> getVehicles(Integer pageNumber, Integer pageSize, String model, String brand, Double fipePrice, Integer type, String year) {
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

    private Page<VehicleResponse> vehicleToList(Page<Vehicle> vehicles){
        List<VehicleResponse> vehicleResponses = vehicles.stream()
                .map(this::convertToVehicleResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(vehicleResponses, PageRequest.of(vehicles.getNumber(), vehicles.getSize()), vehicles.getTotalElements());
    }

    private VehicleResponse convertToVehicleResponse(Vehicle vehicle) {
        Year year = vehicle.getYear();
        Model model = year.getModel();
        Brand brand = model.getBrand();
        VehicleType vehicleType = brand.getVehicleType();
        FipePrice fipePrice = vehicle.getFipePrice();

        return new VehicleResponse(vehicle.getId(), model.getName(), brand.getName(), fipePrice == null ? 0 : fipePrice.getPrice(), vehicleType.name(), year.getName().split(" ")[0]);
    }

    public Brand addBrand(BrandDTO brandDTO) {
        Brand brand = new Brand();

        BeanUtils.copyProperties(brandDTO, brand);

        return brandRepository.save(brand);
    }

    public Model addModel(ModelDTO modelDTO) {
        Model model = new Model();

        BeanUtils.copyProperties(modelDTO, model);

        var brand = brandRepository.findByName(modelDTO.brandName());

        if (brand.isPresent())
            return modelRepository.save(model);
        else
            throw new RuntimeException("Brand not found");
    }


    public VehicleResponse addVehicle(AddVehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();

        BeanUtils.copyProperties(vehicleDTO, vehicle);

        var model = modelRepository.findByName(vehicleDTO.model());
        var brand = brandRepository.findByName(vehicleDTO.brand());

        Year year = new Year();

        if (model.isEmpty()) throw new RuntimeException("Model not valid");
        if (brand.isEmpty()) throw new RuntimeException("Brand not valid");

        year.setId(UUID.randomUUID());
        year.setName(vehicleDTO.year());
        year.setVehicle(vehicle);
        year.setModel(model.get());
        year.setUrlPathName(vehicleDTO.year().replace(" ", "-"));

        vehicle.setId(UUID.randomUUID());
        vehicle.setYear(year);
        vehicle.getYear().setModel(model.get());
        vehicle.getYear().getModel().setBrand(brand.get());
        vehicle.getYear().getModel().getBrand().setVehicleType(VehicleType.fromValue(vehicleDTO.carType()));

        addYear(year);

        vehicleRepository.save(vehicle);

        return convertToVehicleResponse(vehicle);
    }

    public void addYear(Year year){
        yearRepository.save(year);
    }

}
