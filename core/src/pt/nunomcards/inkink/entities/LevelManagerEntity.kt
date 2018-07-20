package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.GameMode
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.multiplayer.MultiplayerHandler
import pt.nunomcards.inkink.utils.IsometricCoords
import java.util.*

/**
 * Created by nuno on 13/07/2018.
 */
class LevelManagerEntity(
        mode: GameMode,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera,
        color: PaintColor
) : BaseEntity(batch, world, camera) {

    private val level: LevelEntity

    init {
        val player = Player(id = UUID.randomUUID().toString(), coordsIso = IsometricCoords(0,0), team = color, currentPlayer = true)
        level = LevelEntity(player, batch, world, camera)

        if(mode == GameMode.MULTIPLAYER){
            MultiplayerHandler.init(level)
        }
    }

    override fun render() {
        // Render
        level.render()
    }

    override fun dispose() {

    }

}