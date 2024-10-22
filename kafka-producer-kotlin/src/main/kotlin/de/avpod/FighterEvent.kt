package de.avpod

data class FighterEvent(val type: EventType, val fighterId: String, val fighterName: String)

enum class EventType {
    FIGHTER_JOINED,
    FIGHTER_QUIT
}
