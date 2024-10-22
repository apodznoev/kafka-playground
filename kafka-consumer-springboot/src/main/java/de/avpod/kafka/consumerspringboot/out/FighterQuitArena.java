package de.avpod.kafka.consumerspringboot.out;

import de.avpod.kafka.consumerspringboot.domain.Fighter;
import org.springframework.context.ApplicationEvent;

public class FighterQuitArena extends ApplicationEvent {
    private final Fighter fighter;
    public FighterQuitArena(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
    }

    public Fighter getFighter() {
        return fighter;
    }
}
