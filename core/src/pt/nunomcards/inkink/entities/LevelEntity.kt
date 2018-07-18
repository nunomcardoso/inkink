package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.Player
import java.util.*

/**
 * Created by nuno on 13/07/2018.
 */
open class LevelEntity(
        player: Player,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : BaseEntity(batch, world, camera) {

    val currentPlayer: PlayerEntity
    val players: List<PlayerEntity> = LinkedList()
    val tileobj: List<TileObjectEntity> = LinkedList()

    val arena: ArenaEntity
    val hud: HUDEntity

    init {
        arena = ArenaEntity(10,10,batch, world, camera)
        currentPlayer = PlayerEntity(player, arena, batch, world, camera)

        hud = HUDEntity(currentPlayer,batch, world, camera)
    }

    override fun render() {
        // DRAW HUD
        hud.render()

        // DRAW Arena
        arena.render()

        // DRAW Objects
        //tileobj.forEach { e -> e.render() }

        // DRAW Players
        currentPlayer.render()
        //players.forEach { e -> e.render() }
    }

    override fun dispose() {
        arena.dispose()
        currentPlayer.dispose()
        players.forEach { e -> e.dispose() }
        hud.dispose()
    }
}