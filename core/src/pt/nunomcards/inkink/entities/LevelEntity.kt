package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.Level
import java.util.*

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

    val players: ArrayList<PlayerEntity> = ArrayList()
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
        players.forEach { e -> e.render() }
    }

    fun useBomb(){
        level.arena.placeBombInk(currentPlayer.player.coordsIso, currentPlayer.player.team)
    }

    fun useCannon(){
        level.arena.shootCannonInk(currentPlayer.player.coordsIso, currentPlayer.player.team)
    }

    override fun dispose() {
        arenaEntity.dispose()
        currentPlayer.dispose()
        players.forEach { e -> e.dispose() }
    }
}