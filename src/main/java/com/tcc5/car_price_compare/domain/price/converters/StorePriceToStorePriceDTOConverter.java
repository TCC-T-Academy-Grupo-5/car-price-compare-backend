package com.tcc5.car_price_compare.domain.price.converters;

import com.tcc5.car_price_compare.domain.price.StorePrice;
import com.tcc5.car_price_compare.domain.price.dto.StorePriceDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StorePriceToStorePriceDTOConverter implements Converter<StorePrice, StorePriceDTO> {
    @Override
    public StorePriceDTO convert(StorePrice source) {
        return new StorePriceDTO(
                source.getVehicle().getId(),
                source.getStore(),
                source.getPrice(),
                source.getDealUrl(),
                source.getScrappingDate()
        );
    }
}
