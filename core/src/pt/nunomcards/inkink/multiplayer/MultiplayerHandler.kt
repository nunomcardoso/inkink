package pt.nunomcards.inkink.multiplayer

import io.socket.client.IO
import io.socket.client.Socket
import com.badlogic.gdx.Gdx
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import pt.nunomcards.inkink.entities.MultiPlayerLevelEntity
import pt.nunomcards.inkink.gamelogic.MultiPlayerLevelLogic
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.model.Tile
import pt.nunomcards.inkink.model.Weapon
import pt.nunomcards.inkink.utils.CartesianCoords
import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 19/07/2018.
 */
// Singleton
object MultiplayerHandler{

    private lateinit var socket: Socket
    private var socketid: String? = null

    private lateinit var level: MultiPlayerLevelEntity
    private lateinit var logic: MultiPlayerLevelLogic

    // To check if game mode is multiplayer
    private var isGameModeMultiplayer: Boolean = false

    // INIT
    fun init(logic: MultiPlayerLevelLogic, level: MultiPlayerLevelEntity, ip: String) {
        isGameModeMultiplayer = true

        this.level = level
        this.logic = logic

        try {
            socket = IO.socket("http://$ip")
            socket.connect()

            configSocketEvents()
        } catch (e: Exception) {
            throw CouldntConnectToServerException()
        }
    }

    // END
    fun end(){
        socket.disconnect()
        isGameModeMultiplayer = false
    }

    // PLAYER MOVED
    fun moveCurrentPlayer(id: String, coords: IsometricCoords){
        if(!isGameModeMultiplayer) return

        val data = JSONObject()
        try {
            data.put("id", id)
            data.put("row", coords.row)
            data.put("col", coords.col)

            socket.emit(MultiplayerEvent.PLAYER_MOVED.value, data)
        } catch (e: JSONException) {
            println(e.message)
        }
    }

    // ADD PLAYER
    private fun addCurrentPlayer(){
        if(!isGameModeMultiplayer) return

        val curPlayer = level.currentPlayer.player

        val data = JSONObject()
        try {
            data.put("id", socketid)
            data.put("color", curPlayer.team.name)
            data.put("row", curPlayer.coordsIso.row)
            data.put("col", curPlayer.coordsIso.col)

            socket.emit(MultiplayerEvent.ADD_CURRENT_PLAYER.value, data)
        } catch (e: JSONException) {
            println(e.message)
        }
    }
    // START GAME
    fun startGame(){
        if(!isGameModeMultiplayer) return
        socket.emit(MultiplayerEvent.START_GAME.value, "")
    }
    // END GAME
    fun endGame(){
        if(!isGameModeMultiplayer) return
        socket.emit(MultiplayerEvent.END_GAME.value, "")
    }

    // PAINT TILE
    fun paintTile(color: PaintColor, coords: IsometricCoords){
        if(!isGameModeMultiplayer) return

        val data = JSONObject()
        try {
            data.put("color", color.ordinal)
            data.put("row", coords.row)
            data.put("col", coords.col)
            socket.emit(MultiplayerEvent.PAINTED_TILE.value, data)
        } catch (e: JSONException) {
            println(e.message)
        }
    }

    // USE WEAPON
    fun useWeapon(color: PaintColor, coords: IsometricCoords, weaponType: Weapon.WeaponType){
        if(!isGameModeMultiplayer) return

        val data = JSONObject()
        try {
            data.put("color", color.ordinal)
            data.put("row", coords.row)
            data.put("col", coords.col)
            data.put("weapon", weaponType.ordinal)

            socket.emit(MultiplayerEvent.WEAPON_USED.value, data)
        } catch (e: JSONException) {
            println(e.message)
        }
    }

    // SOCKET CONFIGURATION
    private fun configSocketEvents() {
        socket
        /**
         *  CONNECTED
         */
        .on(Socket.EVENT_CONNECT) {
            Gdx.app.log("SocketIO", "Connected")
        //}
        ///**
        // *  SOCKET ID
        // */
        //.on(MultiplayerEvent.SOCKET_ID.value) { args ->
            //val data = args[0] as JSONObject
            try {
                socketid = socket.id()
                Gdx.app.log("SocketIO", "My ID: " + socketid)

                // Place player randomly in the arenaEntity
                //val row = Random().nextInt(level.arenaEntity.rows) + 1
                //val col = Random().nextInt(level.arenaEntity.cols) + 1
                //level.currentPlayer.player.id = socketid!!
                //level.currentPlayer.placePlayer(IsometricCoords(row,col))

                // should move current player?
                addCurrentPlayer()
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting ID")
            }
        }
        /**
        *  GET BOARD
        */
        .on(MultiplayerEvent.GET_BOARD.value) { args ->
            Gdx.app.log("SocketIO", "Getting Board")

            val data = args[0] as JSONArray // exception
            try {
                var map: Array<Array<Tile>> = Array(10, {Array(10, { Tile() })})

                for (i in 0 until data.length()) {
                    val r = data[i] as JSONArray
                    for(j in 0 until r.length()){
                        val color = r.optInt(j)
                        map[i][j].color = PaintColor.values()[color]
                    }
                }
                level.level.arena.createFromRemote(map)
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting Board")
            }
        }
        /**
         *  GET INIT TIME
         */
        .on(MultiplayerEvent.INIT_TIMER.value) { args ->
            Gdx.app.log("SocketIO", "Getting Board")

            val data = args[0] as JSONObject
            try {
                val initTime = data.getLong("init")

                logic.initTime = initTime
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting Init Time")
            }
        }
        /**
         *  NEW PLAYER
         */
        .on(MultiplayerEvent.NEW_PLAYER.value) { args ->
            Gdx.app.log("SocketIO", "Getting New PlayerID")

            val data = args[0] as JSONObject
            try {
                val newPid = data.getString("id")
                val team = data.getString("color")
                val row = data.getInt("row")
                val col = data.getInt("col")

                Gdx.app.log("SocketIO", "New Player Connect: " + newPid)

                val coords = IsometricCoords(row, col)

                level.addRemotePlayer(Player(id = newPid, team = PaintColor.valueOf(team), coordsIso = coords), coords)
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting New PlayerID")
            }
        }
        /**
         *  GET PLAYERS
         */
        .on(MultiplayerEvent.GET_PLAYERS.value) { args ->
            Gdx.app.log("SocketIO", "Getting Players")
            val objects = args[0] as JSONArray
            try {
                // Clean player list
                level.clearRemotePlayers()
                for (i in 0 until objects.length()) {
                    val newPid = objects.getJSONObject(i).getString("id")
                    val team = objects.getJSONObject(i).getString("color")
                    val row = objects.getJSONObject(i).getInt("row")
                    val col = objects.getJSONObject(i).getInt("col")

                    val coords = IsometricCoords(row,col)

                    level.addRemotePlayer(Player(id = newPid, team = PaintColor.valueOf(team),coordsIso = coords), coords)
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
                val color = data.getInt("color")

                level.arenaEntity.colorTile(row, col, PaintColor.values()[color])
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting disconnected PlayerID")
            }
        }
        /**
         *  WEAPON USED
         */
        .on(MultiplayerEvent.WEAPON_USED.value) { args ->
            val data = args[0] as JSONObject
            try {
                val row = data.getInt("row")
                val col = data.getInt("col")
                val color = data.getInt("color")
                val weaponOrdinal = data.getInt("weapon")

                if(weaponOrdinal == 0)
                    level.level.arena.placeBombInk(IsometricCoords(row,col), PaintColor.values()[color])
                if(weaponOrdinal == 1)
                    level.level.arena.shootCannonInk(IsometricCoords(row,col), PaintColor.values()[color])

            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting disconnected PlayerID")
            }
        }
        /**
         *  PLAYER MOVED
         */
        .on(MultiplayerEvent.PLAYER_MOVED.value) { args ->
            Gdx.app.log("SocketIO", "Player Moving")
            val data = args[0] as JSONObject
            try {
                val playerId = data.getString("id")
                val row = data.getInt("row")
                val col = data.getInt("col")

                val isoCoords = IsometricCoords(row, col)

                level.moveRemotePlayer(playerId, isoCoords)
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error player moving")
            }
        }
        /**
         *  PLAYER DISCONNECTED
         */
        .on(MultiplayerEvent.PLAYER_DISCONNECT.value) { args ->
            Gdx.app.log("SocketIO", "Player disconnected")
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