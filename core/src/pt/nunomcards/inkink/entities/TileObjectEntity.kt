package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.TileObject

/**
 * Created by nuno on 18/07/2018.
 */
class TileObjectEntity(
        private val obj: TileObject,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : BaseEntity(batch, world, camera) {


    override fun render() {

    }

    override fun dispose() {

    }
}