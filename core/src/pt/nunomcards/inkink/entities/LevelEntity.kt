package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.Level
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by nuno on 13/07/2018.
 */
abstract class LevelEntity(
        val level: Level,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : BaseEntity(batch, world, camera) {

    val arenaEntity: ArenaEntity
    val currentPlayer: PlayerEntity

    val players: ConcurrentHashMap<String, PlayerEntity> = ConcurrentHashMap()
    val tileobj: ArrayList<TileObjectEntity> = ArrayList()

    init {
        arenaEntity = ArenaEntity(level.arena,batch, world, camera)
        currentPlayer = PlayerEntity(level.currentPlayer, arenaEntity, batch, world, camera)
    }

    override fun render() {
        // DRAW Arena
        arenaEntity.render()

        // DRAW Objects
        tileobj.forEach { e -> e.render() }

        // DRAW Players
        currentPlayer.render()
        players.forEach { (k,v) ->
            v.render()
        }
    }

    override fun dispose() {
        arenaEntity.dispose()
        currentPlayer.dispose()
        players.forEach { (k,v) -> v.dispose() }
    }
}