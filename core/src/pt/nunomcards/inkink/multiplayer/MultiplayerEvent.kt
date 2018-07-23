package pt.nunomcards.inkink.multiplayer

/**
 * Created by nuno on 20/07/2018.
 */
enum class MultiplayerEvent(val value: String) {
    SOCKET_ID("socketId"),
    PAINTED_TILE("paintTile"),
    PLAYER_MOVED("playerMoved"),
    ADD_CURRENT_PLAYER("addCurrentPlayer"),
    PLAYER_DISCONNECT("playerDisconnected"),
    GET_PLAYERS("getPlayers"),
    NEW_PLAYER("newPlayer"),
    WEAPON_USED("weaponUsed"),
    GET_BOARD("getBoard"),
    INIT_TIMER("getInitTime"),
    START_GAME("startGame"),
    END_GAME("endGame"),
}