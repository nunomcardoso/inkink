package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.Level
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.utils.CartesianCoords

/**
 * Created by nuno on 20/07/2018.
 */
class MultiPlayerLevelEntity (
        level: Level,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : LevelEntity(level, batch, world, camera) {

    fun addRemotePlayer(remPlayer: Player, coords: CartesianCoords){
        val p = PlayerEntity(remPlayer, arenaEntity, batch, world, camera)
        //p.placePlayer(coords)
        players.add(p)
    }

    fun removeRemotePlayer(remPlayerId: String){
        players.remove(players.first { p -> p.player.id.equals(remPlayerId) })
    }

    fun moveRemotePlayer(remPlayerId: String, coords: CartesianCoords){
        val p = players.first { p -> p.player.id.equals(remPlayerId) }
        p.placePlayer(coords)
    }
}