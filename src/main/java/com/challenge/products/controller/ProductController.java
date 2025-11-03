package com.challenge.products.controller;

import com.challenge.products.dto.*;
import com.challenge.products.mapper.ProductMapper;
import com.challenge.products.model.Price;
import com.challenge.products.model.Product;
import com.challenge.products.service.PriceService;
import com.challenge.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final PriceService priceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@RequestBody ProductDto dto) {
        Product saved = productService.create(dto);
        return dto;
    }

    @PostMapping("/{id}/prices")
    @ResponseStatus(HttpStatus.CREATED)
    public PriceDto addPrice(@PathVariable Long id, @RequestBody PriceDto dto) {
        Price p = priceService.addPrice(id, dto);
        PriceDto result = new PriceDto();
        result.setValue(p.getValue());
        result.setInitDate(p.getInitDate());
        result.setEndDate(p.getEndDate());
        return result;
    }

    @GetMapping("/{id}/prices")
    public ProductDto getAllPrices(@PathVariable Long id) {
        Product product = productService.getById(id);

        return ProductMapper.toDto(product);
    }

    @GetMapping(value = "/{id}/prices", params = "date")
    public PriceRequestDto getPriceAtDate(@PathVariable Long id,
                                   @RequestParam("date")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Price p = priceService.getPriceAtDate(id, date);
        PriceRequestDto result = new PriceRequestDto();
        result.setValue(p.getValue());

        return result;
    }
}
