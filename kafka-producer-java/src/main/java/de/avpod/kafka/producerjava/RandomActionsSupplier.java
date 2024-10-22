package de.avpod.kafka.producerjava;

import de.avpod.kafka.producerjava.out.Action;
import de.avpod.kafka.producerjava.out.Aim;
import de.avpod.kafka.producerjava.out.Maneuver;

import java.util.Random;
import java.util.function.Supplier;

public class RandomActionsSupplier implements Supplier<Action> {
    final Random random = new Random();

    @Override
    public Action get() {
        return new Action(
                Maneuver.values()[random.nextInt(Maneuver.values().length)],
                Aim.values()[random.nextInt(Aim.values().length)]
        );
    }
}
