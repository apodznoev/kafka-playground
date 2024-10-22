package de.avpod.kafka.consumerspringboot.out;

import de.avpod.kafka.consumerspringboot.domain.Fighter;
import org.springframework.context.ApplicationEvent;

public class FighterJoinedArena extends ApplicationEvent {
    private final Fighter fighter;
    public FighterJoinedArena(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
    }

    public Fighter getFighter() {
        return fighter;
    }
}
