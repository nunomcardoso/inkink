package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.*
import pt.nunomcards.inkink.assetloader.AudioAssets
import pt.nunomcards.inkink.entities.LevelManagerEntity
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.model.GameMode
import pt.nunomcards.inkink.model.Level
import pt.nunomcards.inkink.utils.GdxUtils.Companion.BOX_POSITION_ITERATIONS
import pt.nunomcards.inkink.utils.GdxUtils.Companion.BOX_STEP
import pt.nunomcards.inkink.utils.GdxUtils.Companion.BOX_VELOCITY_ITERATIONS

import pt.nunomcards.inkink.utils.LevelCreator
import pt.nunomcards.inkink.utils.UIFactory
import pt.nunomcards.inkink.utils.Vibration

/**
 * Created by nuno on 04/07/2018.
 */
class LevelScreen : Screen {

    private lateinit var levelManager: LevelManagerEntity

    private val batch: SpriteBatch
    private val game: Game
    private var stage: Stage
    private val debugRenderer: Box2DDebugRenderer

    private val world: World
    private val camera: OrthographicCamera

    constructor(game: Game, level: Level) {
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

        // Level THIS MUST BE AFTER inputProcessor being assigned
        try {
            this.levelManager = LevelManagerEntity(game, batch, world, camera, level)
        } catch (e: Exception){
            game.screen= MultiplayerLobbyScreen(game)
        }

        // BACK BUTTON
        // Should be in the levelManager HUD (but there's no game instance there..)
        val side = w / 6
        val backbutton = UIFactory.createImageButton(button_back)
        backbutton.setSize(side, button_back.height*side/button_back.width)
        backbutton.setPosition(w-side,h-w/20)
        backbutton.addListener { _ ->
            // AUDIO
            AudioAssets.selectSound.play()

            if(level.gameMode == GameMode.SINGLEPLAYER){
                AudioAssets.spLevelMusic.stop()
                game.screen= LevelSelectScreen(game)
            }
            if(level.gameMode == GameMode.MULTIPLAYER){
                AudioAssets.mpLevelMusic.stop()
                game.screen= MultiplayerLobbyScreen(game)
            }
            Vibration.vibrate()
            true
        }
        stage.addActor(backbutton)
    }

    private val button_back = Texture("button-back.png")
    // DIMENSIONS
    private val h = Gdx.graphics.height.toFloat()
    private val w = Gdx.graphics.width.toFloat()

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
        levelManager.render()

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