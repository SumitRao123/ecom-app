package com.ecom.ecom_application.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long userId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
