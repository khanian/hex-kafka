package com.khy.hex.kafka.application.service;

import com.khy.hex.kafka.application.poit.in.KafkaUseCase;
import com.khy.hex.kafka.application.poit.out.KafkaOutPort;
import com.khy.hex.kafka.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService implements KafkaUseCase {

    private final KafkaOutPort kafkaOutPort;

    @Override
    public void receiveRequest(Order order) throws Exception {
        Boolean isOk = false;
        try {
            isOk = true;
        } catch (Exception e) {
            log.error(">>> service error {}", e.getMessage());
            isOk = false;
        }
        log.info(">>> service isOk is {}", isOk);

        Order resultOrder = order.changeCurrentState(order, isOk);
        if (resultOrder != null) kafkaOutPort.sendResponse(resultOrder); // produce message
    }
}
