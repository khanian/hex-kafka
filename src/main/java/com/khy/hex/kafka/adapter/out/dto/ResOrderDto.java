package com.khy.hex.kafka.adapter.out.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ResOrderDto {
    private Long orderId;
    private Long customerId;
    private Long productId;
    private Long amount;
    private String shippingAddress;
    private String currentState;
    private LocalDateTime eventAt;
}
