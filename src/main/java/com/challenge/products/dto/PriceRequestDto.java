package com.challenge.products.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PriceRequestDto {
    private BigDecimal value;
}
