import de.avpod.EventType
import de.avpod.FighterEvent
import java.util.HashMap
import java.util.UUID
import kotlin.random.Random

class EventGenerator {
    var playerCounter = 0;
    var joinedPlayers: MutableSet<String> = HashSet();
    var idToName: MutableMap<String,String> = HashMap();

    @OptIn(ExperimentalStdlibApi::class)
    fun nextEvent(): FighterEvent {
        val fighterName: String
        val fighterId: String
        val eventType: EventType
        if (playerCounter < 2) {
            eventType = EventType.FIGHTER_JOINED
        } else {
            eventType = EventType.entries.get(Random.nextInt(EventType.entries.size))
        }

        return when(eventType) {
            EventType.FIGHTER_JOINED -> {
                fighterId = UUID.randomUUID().toString()
                fighterName = "Player ${Random.nextInt().toHexString(HexFormat.UpperCase)}"
                playerCounter++;
                joinedPlayers.add(fighterName)
                idToName[fighterId] = fighterName
                FighterEvent(
                    eventType,
                    fighterId,
                    fighterName
                )
            }
            EventType.FIGHTER_QUIT -> {
                fighterId = joinedPlayers.first()
                fighterName = idToName.get(fighterId).orEmpty()
                playerCounter--;
                FighterEvent(
                    eventType,
                    fighterId,
                    fighterName
                )
            }

        }
    }
}