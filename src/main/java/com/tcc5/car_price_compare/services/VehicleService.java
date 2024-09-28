package com.tcc5.car_price_compare.services;

import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponse;
import com.tcc5.car_price_compare.domain.vehicle.Brand;
import com.tcc5.car_price_compare.domain.vehicle.Model;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.domain.vehicle.Year;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import com.tcc5.car_price_compare.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.repositories.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    //TODO: add price in the method convertVehicleResponse

    @Autowired
    private VehicleRepository vehicleRepository;

    public Page<VehicleResponse> getVehicles(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);

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

        return new VehicleResponse(vehicle.getId(), model.getName(), brand.getName(), 25, vehicleType.name(), year.getName().split(" ")[0]);
    }
}
