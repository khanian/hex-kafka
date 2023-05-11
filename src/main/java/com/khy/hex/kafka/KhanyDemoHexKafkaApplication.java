package com.khy.hex.kafka;

import com.khy.hex.kafka.common.config.AwsMskSecretConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KhanyDemoHexKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhanyDemoHexKafkaApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner (AwsMskSecretConfig awsMskSecretConfig) {
        return args -> {
            //awsMskSecretConfig.getSecret();
            System.out.println("test");
        };
    }

}
