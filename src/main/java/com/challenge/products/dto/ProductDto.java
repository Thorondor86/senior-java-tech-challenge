package com.challenge.products.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private String name;
    private String description;
    private List<PriceDto> prices;
}
