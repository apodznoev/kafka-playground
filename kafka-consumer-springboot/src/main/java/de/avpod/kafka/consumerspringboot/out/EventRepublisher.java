package de.avpod.kafka.consumerspringboot.out;

import de.avpod.kafka.out.protos.Fighter;
import de.avpod.kafka.out.protos.MatchEvent;
import de.avpod.kafka.out.protos.MatchEventProtos;
import de.avpod.kafka.out.protos.MatchFighterEvent;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventRepublisher {
    private final Logger logger = LoggerFactory.getLogger(EventRepublisher.class);

    @Autowired
    private Producer<String, byte[]> producer;

    @EventListener
    public void onEvent(FighterJoinedArena event) {

        producer.send(new ProducerRecord<>(
                "arena-events",
                "fighter:" + event.getFighter().getId(),
                MatchFighterEvent.newBuilder()
                        .setFighter(Fighter.newBuilder()
                                .setId(event.getFighter().getId())
                                .setName(event.getFighter().getName())
                                .build())
                        .setType(MatchFighterEvent.EventType.JOINED_ARENA)
                        .build()
                        .toByteArray()
        ));
    }

    @EventListener
    public void onEvent(FighterQuitArena event) {
        producer.send(new ProducerRecord<>(
                "arena-events",
                "fighter:" + event.getFighter().getId(),
                MatchFighterEvent.newBuilder()
                        .setFighter(Fighter.newBuilder()
                                .setId(event.getFighter().getId())
                                .setName(event.getFighter().getName())
                                .build())
                        .setType(MatchFighterEvent.EventType.LEFT_ARENA)
                        .build()
                        .toByteArray()
        ));
    }

    @EventListener
    public void onEvent(FighterQuitMatch event) {
        producer.send(new ProducerRecord<>(
                "arena-events",
                "fighter:" + event.getFighter().getId(),
                MatchFighterEvent.newBuilder()
                        .setFighter(Fighter.newBuilder()
                                .setId(event.getFighter().getId())
                                .setName(event.getFighter().getName())
                                .build())
                        .setType(MatchFighterEvent.EventType.LEFT_MATCH)
                        .build()
                        .toByteArray()
        ));
    }

    @EventListener
    public void onEvent(MatchStartedEvent event) {
        producer.send(new ProducerRecord<>(
                "arena-events",
                "match:" + event.getMatchId(),
                MatchEvent.newBuilder()
                        .setFighter(0, Fighter.newBuilder()
                                .setId(event.getFighter1().getId())
                                .setName(event.getFighter1().getName())
                                .build()
                        )
                        .setFighter(1, Fighter.newBuilder()
                                .setId(event.getFighter2().getId())
                                .setName(event.getFighter2().getName())
                                .build()
                        )
                        .setType(MatchEvent.EventType.STARTED)
                        .build()
                        .toByteArray()
        ));
    }
}
