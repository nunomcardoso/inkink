package pt.nunomcards.inkink.gamelogic

/**
 * Created by nuno on 13/07/2018.
 */
enum class GameState {
    // COMMON
    RUNNING, ENDED,
    // SinglePlayer
    PAUSED,
    // Multiplayer
    WAITING, READY
}