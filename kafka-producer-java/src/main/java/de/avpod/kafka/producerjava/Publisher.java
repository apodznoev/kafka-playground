package de.avpod.kafka.producerjava;

import de.avpod.kafka.producerjava.out.Action;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.function.Function;
import java.util.function.Supplier;

public class Publisher {
    private final Supplier<Action> eventsSupplier;
    private final Function<Action, String> eventsSerializer;
    private final Function<String, ProducerRecord<String, String>> mapper;
    private final KafkaProducer<String, String> producer;

    public Publisher(Supplier<Action> eventsSupplier,
                     Function<Action, String> eventsSerializer,
                     Function<String, ProducerRecord<String, String>> mapper, KafkaProducer<String, String> producer) {
        this.eventsSupplier = eventsSupplier;
        this.eventsSerializer = eventsSerializer;
        this.mapper = mapper;
        this.producer = producer;
    }

    void start(CallbackListener listener) {
        PublishControl publishControl = new PublishControl();
        while (!publishControl.terminated()) {
            publishControl.waitBeforePublishIfNeeded();
            final Action event = eventsSupplier.get();
            final String serialized = eventsSerializer.apply(event);
            final ProducerRecord<String, String> record = mapper.apply(serialized);
            producer.send(record, (metadata, exception) -> {
                if (metadata.partition() == -1) {
                    listener.onError(exception);
                    return;
                }

                if (exception != null) {
                    listener.onError(exception);
                    return;
                }

                listener.onSuccess(metadata.offset(), metadata.timestamp());
            });
            publishControl.waitAfterPublishIfNeeded();
        }
    }
}
