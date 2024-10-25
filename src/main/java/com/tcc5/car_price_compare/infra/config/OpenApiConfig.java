package com.tcc5.car_price_compare.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI inventoryOpenApi() {
        return new OpenAPI()
                .info(new Info()
                              .title("Precificar API")
                              .description("REST API with endpoints for managing and retrieving information about vehicles, FIPE prices and online deals. It also allows users to manage notifications and their favorite items")
                              .version("v0.0.1"));

    }
}
