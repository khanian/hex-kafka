package com.khy.hex.kafka.adapter.in.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long orderId;
    private Long customerId;
    private Long productId;
    private Long amount;
    private String shippingAddress;
    private String currentState;
    private LocalDateTime eventAt;
}
