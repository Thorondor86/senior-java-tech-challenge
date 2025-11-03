package com.challenge.products.service;

import com.challenge.products.dto.PriceDto;
import com.challenge.products.exception.BusinessRuleException;
import com.challenge.products.exception.ResourceNotFoundException;
import com.challenge.products.mapper.PriceMapper;
import com.challenge.products.model.Price;
import com.challenge.products.model.Product;
import com.challenge.products.repository.PriceRepository;
import com.challenge.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Price addPrice(Long productId, PriceDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        validateDates(dto.getInitDate(), dto.getEndDate());
        validateNoOverlap(productId, dto.getInitDate(), dto.getEndDate());

        Price price = new Price();
        price.setProduct(product);
        price.setValue(dto.getValue());
        price.setInitDate(dto.getInitDate());
        price.setEndDate(dto.getEndDate());

        return priceRepository.save(price);
    }

    private void validateDates(LocalDate init, LocalDate end) {
        if (end != null && end.isBefore(init)) {
            throw new BusinessRuleException("La fecha final no puede ser anterior a la inicial");
        }
    }

    private void validateNoOverlap(Long productId, LocalDate init, LocalDate end) {
        List<Price> existing = priceRepository.findAllByProduct(productId);
        for (Price p : existing) {
            LocalDate pEnd = p.getEndDate() == null ? LocalDate.MAX : p.getEndDate();
            LocalDate newEnd = end == null ? LocalDate.MAX : end;
            boolean overlap = !(newEnd.isBefore(p.getInitDate()) || init.isAfter(pEnd));
            if (overlap) {
                throw new BusinessRuleException("Existe un solapamiento con otro precio existente");
            }
        }
    }

    public Price getPriceAtDate(Long productId, LocalDate date) {
        return priceRepository.findPriceByProductAndDate(productId, date)
                .orElseThrow(() -> new ResourceNotFoundException("No hay precio para esa fecha"));
    }

    public List<PriceDto> getAllPrices(Long productId) {
        return priceRepository.findAllByProduct(productId).stream().map(PriceMapper::toDto).collect(Collectors.toList());
    }
}
