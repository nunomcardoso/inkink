package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.Level
import pt.nunomcards.inkink.model.Player

/**
 * Created by nuno on 20/07/2018.
 */
class SinglePlayerLevelEntity(
        level: Level,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : LevelEntity(level, batch, world, camera) {

    init {

    }
}