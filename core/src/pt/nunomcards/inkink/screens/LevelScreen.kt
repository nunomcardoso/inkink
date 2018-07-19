package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.physics.box2d.*
import pt.nunomcards.inkink.entities.SinglePlayerLevelEntity
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.utils.GdxUtils.Companion.BOX_POSITION_ITERATIONS
import pt.nunomcards.inkink.utils.GdxUtils.Companion.BOX_STEP
import pt.nunomcards.inkink.utils.GdxUtils.Companion.BOX_VELOCITY_ITERATIONS

import pt.nunomcards.inkink.utils.LevelCreator

/**
 * Created by nuno on 04/07/2018.
 */
class LevelScreen : Screen {

    private val spLevel: SinglePlayerLevelEntity

    private val batch: SpriteBatch
    private val game: Game
    private var stage: Stage
    private val debugRenderer: Box2DDebugRenderer

    private val world: World
    private val camera: OrthographicCamera

    constructor(game: Game, color: PaintColor) {
        this.game = game

        // CREATE LEVEL
        val creator = LevelCreator.create()
        batch = creator.first
        world = creator.second
        camera = creator.third

        stage = Stage(ScreenViewport(), batch)
        Gdx.input.inputProcessor = stage
        // DEBUG box2d
        debugRenderer = Box2DDebugRenderer()

        // Create the Level Entity
        spLevel = SinglePlayerLevelEntity(batch,world,camera,color)
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

        // LEVEL RENDER
        spLevel.render()

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