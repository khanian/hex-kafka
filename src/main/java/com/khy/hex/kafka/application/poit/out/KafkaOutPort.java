package com.khy.hex.kafka.application.poit.out;

import com.khy.hex.kafka.domain.Order;

public interface KafkaOutPort {
    public void sendResponse(Order order) throws Exception;
}
