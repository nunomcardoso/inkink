package pt.nunomcards.inkink.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * Created by nuno on 18/07/2018.
 */
object LevelCreator {

    fun create(): Triple<SpriteBatch, World, OrthographicCamera>{
        val batch = createBatch()
        val world = createWorld()
        val camera = createCam()

        // Update the batch with our Camera's view and projection matrices
        batch.projectionMatrix = camera.combined
        return Triple(batch, world, camera)
    }

    private fun createBatch(): SpriteBatch{
        return SpriteBatch()
    }

    private fun createWorld(): World{
        val gravity = Vector2(0f, 0f)
        return World(gravity, true)
    }

    private fun createCam(): OrthographicCamera{
        val camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat() / GdxUtils.PPM, Gdx.graphics.height.toFloat() / GdxUtils.PPM)
        // Setting the camera's initial position to the bottom left of the map. The camera's position is in the center of the camera
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f)
        // Update our camera
        camera.update()

        return camera
    }
}