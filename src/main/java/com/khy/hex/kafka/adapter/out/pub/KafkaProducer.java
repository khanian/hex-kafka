package com.khy.hex.kafka.adapter.out.pub;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.khy.hex.kafka.adapter.out.dto.ResOrderDto;
import com.khy.hex.kafka.application.poit.out.KafkaOutPort;
import com.khy.hex.kafka.domain.Order;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaProducer implements KafkaOutPort {

    private final static String PRODUCE_TOPIC = "discount-request-v1";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final ModelMapper modelMapper;

    @Override
    public void sendResponse(Order order) {
        try {
            ResOrderDto resOrderDto = modelMapper.map(order, ResOrderDto.class);
            log.info(">>> resOrderDto. = {}", resOrderDto.toString());

            String jsonInString = objectMapper.writeValueAsString(resOrderDto);
            sendToKafka(PRODUCE_TOPIC, resOrderDto.getCustomerId(), jsonInString);
        } catch (Exception e) {
            log.error(">>> send Response. {}", e.getMessage());
        }
    }

    private void sendToKafka(String produceTopic, Long customerId, String jsonInString) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(produceTopic, String.valueOf(customerId), jsonInString);

        future.addCallback(new KafkaSendCallback<>(){
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Success to send message. topic={}. message={}", produceTopic, jsonInString);
            }
            @Override
            public void onFailure(KafkaProducerException ex) {
                ProducerRecord<Object, Object> record = ex.getFailedProducerRecord();
                log.error(">>> Fail to send Message. topic={}, record={}", produceTopic, record);
            }
        });
    }
}
