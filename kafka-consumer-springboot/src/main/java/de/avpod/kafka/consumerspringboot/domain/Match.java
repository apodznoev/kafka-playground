package de.avpod.kafka.consumerspringboot.domain;

import de.avpod.kafka.consumerspringboot.util.IdGenerator;
import de.avpod.kafka.consumerspringboot.out.FighterQuitMatch;
import de.avpod.kafka.consumerspringboot.out.MatchStartedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Clock;
import java.util.UUID;

public class Match {
    private final Logger log = LoggerFactory.getLogger(Match.class);
    private volatile boolean started = false;
    private final Clock clock = Clock.systemUTC();
    private final ApplicationEventPublisher eventPublisher;
    private volatile Fighter fighter1;
    private volatile Fighter fighter2;
    private final UUID matchId = IdGenerator.nextId();

    public Match(Fighter fighter1, Fighter fighter2, ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public static Match newMatch(Fighter fighter1, Fighter fighter2, ApplicationEventPublisher eventPublisher) {
        return new Match(fighter1, fighter2, eventPublisher);
    }

    public void start() {
        log.info("Match {} started between {} and {}", matchId, fighter1, fighter2);
        started = true;
        eventPublisher.publishEvent(new MatchStartedEvent(matchId, fighter1, fighter2, clock.instant()));
    }

    public boolean hasFighter(Fighter fighter) {
        return fighter1.getName().equals(fighter.getName()) || fighter2.getName().equals(fighter.getName());
    }

    public boolean tryQuit(Fighter fighter) {
        if (hasFighter(fighter)) {
            return false;
        }

        if (started) {
            return false;
        }

        if (fighter1.getName().equals(fighter.getName())) {
            fighter1 = null;
        }

        if (fighter2.getName().equals(fighter.getName())) {
            fighter2 = null;
        }

        log.info("Fighter {} quit the match {}", fighter.getName(), matchId);
        eventPublisher.publishEvent(new FighterQuitMatch(matchId, fighter, clock.instant()));
        started = false;

        return true;

    }
}
