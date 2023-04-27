package com.khy.hex.kafka.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Long customerId;
    private Long productId;
    private Long amount;
    private String shippingAddress;
    private String currentState;
    private LocalDateTime eventAt;

    public Order changeCurrentState(Order order, Boolean isOk){
        String responseState = String.valueOf(order.getCurrentState());
        Order resOrder = null;
        System.out.println(responseState.contains("OK"));
        if (!responseState.contains("OK") && !responseState.contains("FAIL")){
            if (isOk)
                responseState = responseState + "_OK";
            else
                responseState = responseState + "_FAIL";

        resOrder = Order.builder()
                .customerId(order.getCustomerId())
                .orderId(order.getOrderId())
                .productId(order.getProductId())
                .amount(order.getAmount())
                .shippingAddress(order.getShippingAddress())
                .currentState(responseState)
                .eventAt(LocalDateTime.now())
                .build();
        }
        return resOrder;
    }
}
