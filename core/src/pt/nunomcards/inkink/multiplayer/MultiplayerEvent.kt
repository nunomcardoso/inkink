package pt.nunomcards.inkink.multiplayer

/**
 * Created by nuno on 20/07/2018.
 */
enum class MultiplayerEvent(val value: String) {
    SOCKET_ID("socketId"),
    PAINTED_TILE("paintSquare"),
    PLAYER_MOVED("movePlayer"),
    PLAYER_DISCONNECT("playerDisconnected"),
    GET_PLAYERS("getPlayers"),
    NEW_PLAYER("newPlayer"),
}