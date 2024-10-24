package com.tcc5.car_price_compare.application.brand;

import com.tcc5.car_price_compare.application.vehicle.brand.BrandController;
import com.tcc5.car_price_compare.application.vehicle.brand.BrandService;
import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BrandControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BrandService service;

    @InjectMocks
    private BrandController brandController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
    }

    @Test
    public void testGetBrands() throws Exception {
        BrandDTO brandDTO = new BrandDTO(UUID.randomUUID(), "Alfa Romeo", "alfa-romeo", "https://tabelacarros.com/imagens/marcas/Alfa_Romeo.png", VehicleType.CAR);
        List<BrandDTO> brands = Collections.singletonList(brandDTO);
        Page<BrandDTO> brandPage = new PageImpl<>(brands, PageRequest.of(0, 10), brands.size());

        when(service.findAll(isNull(), isNull(), any(Pageable.class))).thenReturn(brandPage);

        mockMvc.perform(get("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNumber", "1")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Alfa Romeo"));

        verify(service, times(1)).findAll(isNull(), isNull(), any(Pageable.class));
    }

    @Test
    public void testGetBrandById() throws Exception {
        UUID brandId = UUID.randomUUID();
        BrandDTO brandDTO = new BrandDTO(brandId, "Alfa Romeo", "alfa-romeo", "https://tabelacarros.com/imagens/marcas/Alfa_Romeo.png", VehicleType.CAR);

        when(service.findById(brandId)).thenReturn(brandDTO);

        mockMvc.perform(get("/brand/{brandId}", brandId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Alfa Romeo"));

        verify(service, times(1)).findById(brandId);
    }
}