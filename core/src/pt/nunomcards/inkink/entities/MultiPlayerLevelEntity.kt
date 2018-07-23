package pt.nunomcards.inkink.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.Level
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 20/07/2018.
 */
class MultiPlayerLevelEntity (
        level: Level,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : LevelEntity(level, batch, world, camera) {

    fun addRemotePlayer(remPlayer: Player, coords: IsometricCoords){
        val p = PlayerEntity(remPlayer, arenaEntity, batch, world, camera)
        p.placePlayer(coords)
        players.put(p.player.id, p)
    }

    fun removeRemotePlayer(remPlayerId: String){
        try {
            players.remove(remPlayerId)
        } catch (e: Exception){
            Gdx.app.log("MULTIPLAYER", "ERROR REMOVING PLAYER")
        }
    }

    fun moveRemotePlayer(remPlayerId: String, coords: IsometricCoords){
        if(remPlayerId == "") return
        try{
            val v = players[remPlayerId]
            v!!.placePlayer(coords)
        } catch (e: Exception){
            Gdx.app.log("MULTIPLAYER", "ERROR MOVING PLAYER")
        }
    }

    fun clearRemotePlayers() {
        players.clear()
    }
}