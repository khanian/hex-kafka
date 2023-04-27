package com.khy.hex.kafka.application.poit.in;

import com.khy.hex.kafka.domain.Order;

public interface KafkaUseCase {
    public void receiveRequest(Order order) throws Exception;
}
