package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.utils.CartesianCoords
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
    private val players: ArrayList<PlayerEntity> = ArrayList()
    val tileobj: ArrayList<TileObjectEntity> = ArrayList()

    val arena: ArenaEntity
    val hud: HUDEntity

    init {
        arena = ArenaEntity(10,10,batch, world, camera)
        currentPlayer = PlayerEntity(player, arena, batch, world, camera)

        hud = HUDEntity(currentPlayer,batch, world, camera)
    }

    fun addRemotePlayer(remPlayer: Player, coords: CartesianCoords){
        val p = PlayerEntity(remPlayer, arena, batch, world, camera)
        p.placePlayer(coords)
        players.add(p)
    }

    fun removeRemotePlayer(remPlayerId: String){
        players.remove(players.first { p -> p.player.id.equals(remPlayerId) })
    }

    fun moveRemotePlayer(remPlayerId: String, coords: CartesianCoords){
        val p = players.first { p -> p.player.id.equals(remPlayerId) }
        p.placePlayer(coords)
    }

    override fun render() {
        // DRAW Arena
        arena.render()

        // DRAW Objects
        //tileobj.forEach { e -> e.render() }

        // DRAW Players
        currentPlayer.render()
        players.forEach { e -> e.render() }

        // DRAW HUD
        hud.render()
    }

    override fun dispose() {
        arena.dispose()
        currentPlayer.dispose()
        players.forEach { e -> e.dispose() }
        hud.dispose()
    }
}