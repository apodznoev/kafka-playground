package de.avpod.kafka.producerjava;

import de.avpod.kafka.producerjava.in.ArenaEventsListener;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ActionsProducerApplication {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(ActionsProducerApplication.class);

    public static void main(String[] args) {
        log.info("Starting application");

        final String bootstrapServers = "localhost:9093";

        final ArenaEventsListener listener = new ArenaEventsListener(bootstrapServers);


        final Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (ExecutorService producerExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            producerExecutor.submit(() -> {
                try (final KafkaProducer<String, String> producer = new KafkaProducer<>(
                        producerProperties, new StringSerializer(), new StringSerializer())) {

                    final Supplier<String> keySupplier = new KeyGenerator();

                    final Publisher publisher = new Publisher(
                            new RandomActionsSupplier(),
                            new JsonSerializer(),
                            (jsonPayload) -> new ProducerRecord<>(
                                    "actions",
                                    keySupplier.get(),
                                    jsonPayload
                            ),
                            producer
                    );

                    publisher.start(new CallbackListener() {
                        @Override
                        public void onSuccess(long offset, long timestamp) {
                            log.info("Message delivered with offset: {} at: {}", offset, timestamp);
                        }

                        @Override
                        public void onError(Exception maybeException) {
                            log.error("Message failed", maybeException);
                        }
                    });
                }
            });
        }
    }
}
