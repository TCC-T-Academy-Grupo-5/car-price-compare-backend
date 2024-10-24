package com.tcc5.car_price_compare.application.model;

import com.tcc5.car_price_compare.application.vehicle.model.ModelController;
import com.tcc5.car_price_compare.application.vehicle.model.ModelService;
import com.tcc5.car_price_compare.domain.vehicle.dto.ModelDTO;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ModelControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ModelService service;

    @InjectMocks
    private ModelController modelController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(modelController).build();
    }

    @Test
    public void testGetModelsByBrandId() throws Exception {
        UUID brandId = UUID.fromString("764260da-841d-4c62-baa4-713f9cd23c32");

        ModelDTO modelDTO = new ModelDTO(
                UUID.fromString("0425b6a2-b9be-4d45-89f8-6ad8367d4164"),
                "Argo",
                "argo",
                "https://tabelacarros.com/imagens/marcas/Fiat.png",
                "Fiat"
        );

        List<ModelDTO> models = Collections.singletonList(modelDTO);
        Page<ModelDTO> modelPage = new PageImpl<>(models, PageRequest.of(0, 1), 43);
        when(service.findModelsByBrandId(eq(brandId), any(Pageable.class))).thenReturn(modelPage);

        MvcResult result = mockMvc.perform(get("/model/brand/{brandId}", brandId.toString())
                        .param("pageSize", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("X-Total-Pages", "43"))
                .andExpect(header().string("X-Total-Elements", "43"))
                .andExpect(header().string("X-Current-Page", "1"))
                .andExpect(header().string("X-Page-Size", "1"))
                .andExpect(jsonPath("$[0].name").value("Argo"))
                .andExpect(jsonPath("$[0].urlPathName").value("argo"))
                .andExpect(jsonPath("$[0].imageUrl").value("https://tabelacarros.com/imagens/marcas/Fiat.png"))
                .andExpect(jsonPath("$[0].brand").value("Fiat"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        System.out.println("\nResponse: " + jsonResponse);
    }
}