package de.avpod.kafka.consumerspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaConsumerSpringbootApplication {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerSpringbootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerSpringbootApplication.class, args);
        log.info("Application started");
    }

}