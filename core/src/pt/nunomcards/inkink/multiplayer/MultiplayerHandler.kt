package pt.nunomcards.inkink.multiplayer

import io.socket.client.IO
import io.socket.client.Socket
import pt.nunomcards.inkink.entities.LevelEntity
import com.badlogic.gdx.Gdx
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.utils.CartesianCoords
import pt.nunomcards.inkink.utils.IsometricCoords
import java.util.*

/**
 * Created by nuno on 19/07/2018.
 */
// Singleton
object MultiplayerHandler{

    private lateinit var socket: Socket
    private var id: String? = null

    private lateinit var level: LevelEntity

    // To check if game mode is multiplayer
    var isGameModeMultiplayer: Boolean = false

    fun init(level: LevelEntity) {
        isGameModeMultiplayer = true

        this.level = level

        try {
            socket = IO.socket("http://194.210.190.29:8080")
            socket.connect()

            configSocketEvents()
        } catch (e: Exception) {
            throw CouldntConnectToServerException()
        }
    }

    /**
     JSON Object
     {
        id: "dfadfa-afdsfads-fadfa",
        x: 1.0f,
        h: 1.0f,
        tileH: 40f
     }
     */
    fun moveCurrentPlayer(id: String, coords: CartesianCoords){
        if(!isGameModeMultiplayer) return

        val data = JSONObject()
        try {
            val convCoords = level.arena.convertToRemoteCoords(coords)
            data.put("id", id)
            data.put("x", convCoords.first)
            data.put("y", convCoords.second)
            data.put("tileH", convCoords.third)

            socket.emit(MultiplayerEvent.PLAYER_MOVED.value, data)
        } catch (e: JSONException) {
        }
    }

    /**
     JSON Object
     {
        color: WHITE,
        row: 1,
        col: 1
     }
     */
    fun paintTile(color: PaintColor, coords: IsometricCoords){
        if(!isGameModeMultiplayer) return

        val data = JSONObject()
        try {
            data.put("color", color.name)
            data.put("row", coords.row)
            data.put("col", coords.col)
            socket.emit(MultiplayerEvent.PLAYER_MOVED.value, data)
        } catch (e: JSONException) {
        }
    }

    // SOCKET CONFIGURATION
    fun configSocketEvents() {
        socket
        /**
         *  CONNECTED
         */
        .on(Socket.EVENT_CONNECT) {
            Gdx.app.log("SocketIO", "Connected")
        }
        /**
         *  SOCKET ID
         */
        .on(MultiplayerEvent.SOCKET_ID.value) { args ->
            val data = args[0] as JSONObject
            try {
                id = data.getString("id")
                Gdx.app.log("SocketIO", "My ID: " + id)

                // Place player randomly in the arena
                val row = Random().nextInt(level.arena.rows) + 1
                val col = Random().nextInt(level.arena.cols) + 1
                level.currentPlayer.placePlayer(IsometricCoords(row,col))

                // should move current player?
                //moveCurrentPlayer(id!!, level.currentPlayer.player.coordsCart)
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting ID")
            }
        }
        /**
         *  NEW PLAYER
         */
        .on(MultiplayerEvent.NEW_PLAYER.value) { args ->
            val data = args[0] as JSONObject
            try {
                val newPid = data.getString("id")
                val team = data.getString("team")
                val x = data.getDouble("x").toFloat()
                val y = data.getDouble("y").toFloat()
                val tileH = data.getDouble("tileH").toFloat()

                Gdx.app.log("SocketIO", "New Player Connect: " + newPid)

                val coords = level.arena.convertFromRemoteCoords(x, y, tileH)

                level.addRemotePlayer(Player(id = newPid, team = PaintColor.valueOf(team)),coords)
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting New PlayerID")
            }
        }
        /**
         *  GET PLAYERS
         */
        .on(MultiplayerEvent.GET_PLAYERS.value) { args ->
            val objects = args[0] as JSONArray
            try {
                for (i in 0 until objects.length()) {
                    val newPid = objects.getJSONObject(i).getString("id")
                    val team = objects.getJSONObject(i).getString("team")
                    val x = objects.getJSONObject(i).getDouble("x").toFloat()
                    val y = objects.getJSONObject(i).getDouble("y").toFloat()
                    val tileH = objects.getJSONObject(i).getDouble("tileH").toFloat()

                    val coords = level.arena.convertFromRemoteCoords(x, y, tileH)

                    level.addRemotePlayer(Player(id = newPid, team = PaintColor.valueOf(team)), coords)
                }
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting Players")
            }
        }
        /**
         *  PAINTED TILE
         */
        .on(MultiplayerEvent.PAINTED_TILE.value) { args ->
            val data = args[0] as JSONObject
            try {
                val row = data.getInt("row")
                val col = data.getInt("col")
                val color = data.getString("color")

                level.arena.colorTile(row, col, PaintColor.valueOf(color))
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting disconnected PlayerID")
            }
        }
        /**
         *  PLAYER MOVED
         */
        .on(MultiplayerEvent.PLAYER_MOVED.value) { args ->
            val data = args[0] as JSONObject
            try {
                val playerId = data.getString("id")
                val coordX = data.getDouble("x").toFloat()
                val coordY = data.getDouble("y").toFloat()
                val tileH = data.getDouble("tileH").toFloat()

                val converted = level.arena.convertFromRemoteCoords(coordX,coordY,tileH)

                level.moveRemotePlayer(playerId, converted)
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting disconnected PlayerID")
            }
        }
        /**
         *  PLAYER DISCONNECTED
         */
        .on(MultiplayerEvent.PLAYER_DISCONNECT.value) { args ->
            val data = args[0] as JSONObject
            try {
                val playerId = data.getString("id")
                level.removeRemotePlayer(playerId)
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting disconnected PlayerID")
            }
        }
    }
}