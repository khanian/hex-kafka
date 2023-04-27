package com.khy.hex.kafka.adapter.in.api;

import com.khy.hex.kafka.adapter.in.dto.ReqOrderDto;
import com.khy.hex.kafka.adapter.out.pub.KafkaProducer;
import com.khy.hex.kafka.application.poit.out.KafkaOutPort;
import com.khy.hex.kafka.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaOutPort kafkaOutPort;
    private final ModelMapper moduleMapper;

    @PostMapping
    public String sendMessage(@RequestBody ReqOrderDto reqOrderDto) throws Exception {
        Order order =  moduleMapper.map(reqOrderDto, Order.class);
        Order buildOrder = Order.builder()
                .customerId(order.getCustomerId())
                .orderId(order.getOrderId())
                .productId(order.getProductId())
                .amount(order.getAmount())
                .shippingAddress(order.getShippingAddress())
                .currentState(order.getCurrentState())
                .eventAt(LocalDateTime.now())
                .build();
        kafkaOutPort.sendResponse(buildOrder);
        log.info(">>> controller message buildOrder = {}", buildOrder);
        return "success";
    }
}
