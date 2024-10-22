package de.avpod.kafka.consumerspringboot.domain;

import de.avpod.kafka.consumerspringboot.out.FighterJoinedArena;
import de.avpod.kafka.consumerspringboot.out.FighterQuitArena;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Arena {
    private final Logger log = LoggerFactory.getLogger(Arena.class);
    private final List<Fighter> availableFighters = new ArrayList<>();
    private final Set<Match> startedMatches = new HashSet<>();

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public Arena(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public synchronized void addFighter(Fighter fighter) {
        if (availableFighters.stream().anyMatch(f -> f.getName().equals(fighter.getName()))) {
            return;
        }

        eventPublisher.publishEvent(new FighterJoinedArena(fighter));

        log.info("Fighter {} added to arena", fighter.getName());

        this.availableFighters.add(fighter);
        if (availableFighters.size() > 1) {
            Fighter fighter1 = availableFighters.removeFirst();
            Fighter fighter2 = availableFighters.removeFirst();
            Match match = Match.newMatch(fighter1, fighter2, eventPublisher);
            startedMatches.add(match);
            match.start();
        }
    }

    public synchronized void quitFighter(Fighter fighter) {
        if (availableFighters.remove(fighter)) {
            eventPublisher.publishEvent(new FighterQuitArena(fighter));
            log.info("Fighter {} quit", fighter);
            return;
        }

        startedMatches.forEach(match -> {
            if (match.hasFighter(fighter)) {
                final boolean quit = match.tryQuit(fighter);
                if (!quit) {
                    log.info("Fighter {} cannot quit the match", fighter);
                } else {
                    log.info("Fighter {} has quit the match", fighter);
                }
            }
        });
    }
}
