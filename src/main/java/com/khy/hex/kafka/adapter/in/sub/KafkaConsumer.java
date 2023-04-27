package com.khy.hex.kafka.adapter.in.sub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.khy.hex.kafka.adapter.in.dto.ReqOrderDto;
import com.khy.hex.kafka.application.poit.in.KafkaUseCase;
import com.khy.hex.kafka.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private static final String CONSUME_TOPIC = "discount-request-v1";

    //private static final String GROUP_ID = "state-consumer-group-id";

    private final KafkaUseCase kafkaUseCase;

    private final ModelMapper modelMapper;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @KafkaListener(topics = CONSUME_TOPIC)
    public void recordListener(String jsonMessage) {
        try {
            ReqOrderDto reqOrderDto = objectMapper.readValue(jsonMessage, ReqOrderDto.class);
            log.info(">>> reqOrderDto. = {}", reqOrderDto.toString());
            Order order = modelMapper.map(reqOrderDto, Order.class);
            log.info(">>> order. = {}", order.toString());
            kafkaUseCase.receiveRequest(order);
        } catch (Exception e) {
            log.error("recordListener ERROR message = {}", jsonMessage, e);
        }
    }
}
