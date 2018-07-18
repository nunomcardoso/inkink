package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World

/**
 * Created by nuno on 13/07/2018.
 */
abstract class BaseEntity(
        protected val batch: SpriteBatch,
        protected val world: World,
        protected val camera: OrthographicCamera) {

    /**
     * Renders this entity
     *
     * Each entity must call batch.begin() and batch.end()
     * Each entity is responsible to render itself
     */
    abstract fun render()

    abstract fun dispose()
}