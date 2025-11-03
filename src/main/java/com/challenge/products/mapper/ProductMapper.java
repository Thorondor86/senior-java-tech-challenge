package com.challenge.products.mapper;

import com.challenge.products.dto.ProductDto;
import com.challenge.products.model.Product;

import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) return null;

        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());

        dto.setPrices(
                product.getPrices().stream()
                        .map(PriceMapper::toDto)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}
