package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.*
import pt.nunomcards.inkink.entities.LevelEntity
import pt.nunomcards.inkink.utils.GdxUtils.Companion.BOX_POSITION_ITERATIONS
import pt.nunomcards.inkink.utils.GdxUtils.Companion.BOX_STEP
import pt.nunomcards.inkink.utils.GdxUtils.Companion.BOX_VELOCITY_ITERATIONS

import pt.nunomcards.inkink.utils.GdxUtils.Companion.PPM
import java.util.logging.Level

/**
 * Created by nuno on 04/07/2018.
 */
class LevelScreen : Screen {

    private val level: LevelEntity


    private val batch = SpriteBatch()
    private val game: Game
    private var stage: Stage
    private val debugRenderer: Box2DDebugRenderer

    private val world: World
    private val camera: OrthographicCamera

    constructor(game: Game, lvl: Int) {
        this.game = game
        stage = Stage(ScreenViewport(), batch)
        Gdx.input.inputProcessor = stage

        // Game implements ApplicationListener that delegates to a screen
        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat() / PPM, Gdx.graphics.height.toFloat() / PPM)
        // Setting the camera's initial position to the bottom left of the map. The camera's position is in the center of the camera
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f)
        // Update our camera
        camera.update()
        // Update the batch with our Camera's view and projection matrices
        batch.projectionMatrix = camera.combined

        world = World(Vector2(0f, 0f), true)
        debugRenderer = Box2DDebugRenderer()


        // Create the Level Entity
        level = LevelEntity()
    }

    override fun hide() {}

    override fun show() {}

    /**
     *          RENDER
     */
    override fun render(delta: Float) {
        camera.update()

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT)

        batch.begin()

        // DRAW Arena

        // Limits of arena

        // DRAW Objects

        // DRAW Players

        batch.end()

        debugRenderer.render(world, camera.combined)

        stage.act(Gdx.graphics.rawDeltaTime)
        stage.draw()

        world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS)
    }

    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}
    override fun dispose() {}

}