package com.challenge.products.mapper;

import com.challenge.products.dto.PriceDto;
import com.challenge.products.model.Price;

public class PriceMapper {

    public static PriceDto toDto(Price price) {
        if (price == null) return null;

        PriceDto dto = new PriceDto();
        dto.setValue(price.getValue());
        dto.setInitDate(price.getInitDate());
        dto.setEndDate(price.getEndDate());
        return dto;
    }
}
