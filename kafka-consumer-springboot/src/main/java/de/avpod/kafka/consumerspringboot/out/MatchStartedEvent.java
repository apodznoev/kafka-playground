package de.avpod.kafka.consumerspringboot.out;

import de.avpod.kafka.consumerspringboot.domain.Fighter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.UUID;

public class MatchStartedEvent extends ApplicationEvent {
    private final UUID matchId;
    private final Fighter fighter1;
    private final Fighter fighter2;

    public MatchStartedEvent(UUID matchId,
                             Fighter fighter1,
                             Fighter fighter2,
                             Instant instant) {
        super(matchId);
        this.matchId = matchId;
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public String getMatchId() {
        return matchId.toString();
    }

    public Fighter getFighter1() {
        return fighter1;
    }

    public Fighter getFighter2() {
        return fighter2;
    }
}
