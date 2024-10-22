package de.avpod.kafka.consumerspringboot.util;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.UUID;

public class IdGenerator {
    public static UUID nextId() {
        return UUID.randomUUID();
    }
}
