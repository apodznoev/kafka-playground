package de.avpod.kafka.consumerspringboot.out;

import de.avpod.kafka.consumerspringboot.domain.Fighter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.UUID;

public class FighterQuitMatch extends ApplicationEvent {
    private final UUID matchId;
    private final Fighter fighter;
    private final Instant instant;

    public FighterQuitMatch(UUID matchId, Fighter fighter, Instant instant) {
        super(matchId);
        this.fighter = fighter;
        this.matchId = matchId;
        this.instant = instant;
    }

    public UUID getMatchId() {
        return matchId;
    }

    public Fighter getFighter() {
        return fighter;
    }

    public Instant getInstant() {
        return instant;
    }
}
