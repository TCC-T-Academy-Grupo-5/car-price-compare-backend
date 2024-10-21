package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.domain.vehicle.dto.BrandDTO;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

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
    public void testGetBrandByUrlPathName() throws Exception {
        String slug = "alfa-romeo";
        BrandDTO brandDTO = new BrandDTO(
                UUID.randomUUID(),
                "Alfa Romeo",
                slug,
                "http://https://tabelacarros.com/imagens/marcas/Alfa_Romeo.png",
                VehicleType.CAR
        );

        when(service.findByUrlPathName(slug)).thenReturn(brandDTO);

        mockMvc.perform(get("/brand/{slug}", slug)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Alfa Romeo"));

        verify(service, times(1)).findByUrlPathName(slug);
        System.out.println("\n\n====================\n====================\nSuccess: " + slug);
    }
}
