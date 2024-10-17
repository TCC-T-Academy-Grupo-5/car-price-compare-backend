package com.tcc5.car_price_compare.vehicle;

import com.tcc5.car_price_compare.domain.price.StorePrice;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import com.tcc5.car_price_compare.domain.price.dto.StorePricesRequestDTO;
import com.tcc5.car_price_compare.domain.response.vehicle.VehicleResponseDTO;
import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import com.tcc5.car_price_compare.repositories.vehicle.BrandRepository;
import com.tcc5.car_price_compare.repositories.vehicle.ModelRepository;
import com.tcc5.car_price_compare.repositories.vehicle.VehicleRepository;
import com.tcc5.car_price_compare.repositories.vehicle.YearRepository;
import com.tcc5.car_price_compare.services.ConversionService;
import com.tcc5.car_price_compare.services.StatisticService;
import com.tcc5.car_price_compare.services.StorePriceService;
import com.tcc5.car_price_compare.services.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private StatisticService statisticService;

    @Mock
    private ConversionService conversionService;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private YearRepository yearRepository;

    @Mock
    private StorePriceService storePriceService;

    @Mock
    private WebClient webClient;

    @Test
    void testGetVehicles() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Vehicle> vehicleList = List.of(mock(Vehicle.class), mock(Vehicle.class));
        Page<Vehicle> vehiclePage = new PageImpl<>(vehicleList, pageable, vehicleList.size());

        given(this.vehicleRepository.findAll(any(Specification.class), eq(pageable))).willReturn(vehiclePage);
        given(this.conversionService.convertToVehicleResponse(any(Vehicle.class))).willReturn(mock(VehicleResponseDTO.class));

        Page<VehicleResponseDTO> result = vehicleService.getVehicles("model", "brand", 50000.0, 1, "2022", pageable);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testGetVehicleById() {
        UUID vehicleId = UUID.randomUUID();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        VehicleResponseDTO vehicleResponseDTO = mock(VehicleResponseDTO.class);

        given(this.vehicleRepository.findById(vehicleId)).willReturn(Optional.of(vehicle));
        given(this.conversionService.convertToVehicleResponse(vehicle)).willReturn(vehicleResponseDTO);

        VehicleResponseDTO result = this.vehicleService.getVehicleById(vehicleId.toString());

        assertEquals(vehicleResponseDTO, result);
    }

    @Test
    void testGetVehicleStorePricesFromLocalServiceSuccess() {
        UUID vehicleId = UUID.randomUUID();
        List<StorePrice> mockStorePrices = List.of(mock(StorePrice.class), mock(StorePrice.class));

        given(this.storePriceService.getCurrentDayDeals(vehicleId)).willReturn(mockStorePrices);
        given(this.conversionService.convertToStorePriceDTO(any(StorePrice.class))).willReturn(mock(StorePriceDTO.class));

        List<StorePriceDTO> result = this.vehicleService.getVehicleStorePrices(vehicleId);

        assertEquals(2, result.size());
        verify(this.conversionService, times(2)).convertToStorePriceDTO(any(StorePrice.class));
        verify(webClient, never()).post();
    }
}
