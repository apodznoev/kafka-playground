package de.avpod.kafka.producerjava;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class KeyGenerator implements Supplier<String> {
    private final AtomicLong counter = new AtomicLong();
    @Override
    public String get() {
        return String.valueOf(counter.incrementAndGet());
    }
}
