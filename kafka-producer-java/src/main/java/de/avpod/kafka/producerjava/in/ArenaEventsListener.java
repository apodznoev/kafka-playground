package de.avpod.kafka.producerjava.in;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArenaEventsListener {
    private final static Logger log = LoggerFactory.getLogger(ArenaEventsListener.class);
    private final static String IN_TOPIC = "arena-events";
    private final static Properties consumerProperties = new Properties();

    public ArenaEventsListener(String bootstrapServers) {
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "consumer-javaproducer");
    }

    public void start() {
        try (ExecutorService consumerExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            consumerExecutor.submit(() -> {
                try (final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(
                        consumerProperties, new StringDeserializer(), new StringDeserializer()
                )) {
                    consumer.subscribe(Set.of(IN_TOPIC));
                    while (true) {
                        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                        records.forEach(record -> {
                            String key = record.key();
                            String value = record.value();
                            log.info("Got new record {}:{}", key, value);
                        });
                    }
                }
            });
        }
    }
}
