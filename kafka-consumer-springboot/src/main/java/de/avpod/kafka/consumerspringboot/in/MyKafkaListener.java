package de.avpod.kafka.consumerspringboot.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.avpod.kafka.consumerspringboot.domain.Arena;
import de.avpod.kafka.consumerspringboot.domain.Fighter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyKafkaListener {
    private final Logger logger = LoggerFactory.getLogger(MyKafkaListener.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Arena arena;

    @KafkaListener(topics = "actions")
    public void listen1(String message) {
        logger.info("Received Message for actions: " + message);
        try {
            final Map<String, Object> payload = objectMapper.readValue(message, new TypeReference<>() {});
            final Object actionType = payload.get("actionType");
            final Object aimedTo = payload.get("aim");
        } catch (JsonProcessingException e) {
            logger.error("Cannot process json payload", e);
        }
    }

    @KafkaListener(topics = "player-events", topicPattern = "")
    public void listen2(String message) {
        logger.info("Received Message for player-events: " + message);
        try {
            final Map<String, Object> payload = objectMapper.readValue(message, new TypeReference<>() {});
            final Object eventType = payload.get("type");
            final String fighterId = (String) payload.get("fighterId");
            final String fighterName = (String) payload.get("fighterName");

            if(eventType.equals("FIGHTER_JOINED")) {
                arena.addFighter(new Fighter(fighterId, fighterName));
            }

            if(eventType.equals("FIGHTER_QUIT")) {
                arena.quitFighter(new Fighter(fighterId, fighterName));
            }

        } catch (JsonProcessingException e) {
            logger.error("Cannot parse JSON",e);
        }
    }
}
