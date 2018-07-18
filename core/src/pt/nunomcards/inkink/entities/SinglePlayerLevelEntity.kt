package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 13/07/2018.
 */
class SinglePlayerLevelEntity(
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : BaseEntity(batch, world, camera) {

    private val level: LevelEntity

    init {
        val player = Player("nuno")
        level = LevelEntity(player, batch, world, camera)
    }

    override fun render() {
        // Render
        level.render()
    }

    override fun dispose() {

    }

    fun setPlayerCoords(isometricCoords: IsometricCoords){
        level.currentPlayer.placePlayer(isometricCoords)
    }
}