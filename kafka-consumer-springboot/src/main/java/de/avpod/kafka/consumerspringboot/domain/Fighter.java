package de.avpod.kafka.consumerspringboot.domain;

public class Fighter {
    private final String id;
    private final String name;

    public Fighter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
