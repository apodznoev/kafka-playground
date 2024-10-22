package de.avpod.kafka.consumerspringboot.infra;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        final Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9093"
        );
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "consumer-1-groupId");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            final ConsumerFactory<String, String> consumerFactory) {

        final ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

//    @Bean
//    public ProducerFactory<String, String> kafkaProducerFactory() {
//        final Map<String, Object> props = new HashMap<>();
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "localhost:9093"
//        );
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                "my-group-id");
//        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(), new StringSerializer());
//    }
//
    @Bean
    public Producer<String, String> kafkaProducer(ProducerFactory<String, String> producerFactory) {
        return producerFactory.createProducer();
    }
}
