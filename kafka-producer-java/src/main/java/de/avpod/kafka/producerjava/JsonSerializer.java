package de.avpod.kafka.producerjava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.avpod.kafka.producerjava.out.Action;

import java.util.function.Function;

public class JsonSerializer implements Function<Action, String> {
    private final ObjectMapper objectMapper;

    public JsonSerializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String apply(Action action) {
        try {
            return objectMapper.writeValueAsString(action);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
