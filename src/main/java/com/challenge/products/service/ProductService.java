package com.challenge.products.service;

import com.challenge.products.dto.ProductDto;
import com.challenge.products.exception.ResourceNotFoundException;
import com.challenge.products.mapper.ProductMapper;
import com.challenge.products.model.Product;
import com.challenge.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(ProductDto dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        return productRepository.save(p);
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }
}
