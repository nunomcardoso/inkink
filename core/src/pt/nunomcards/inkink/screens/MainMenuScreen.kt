package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pt.nunomcards.inkink.utils.UIFactory
import pt.nunomcards.inkink.utils.Vibration

/**
 * Created by nuno on 04/07/2018.
 */
class MainMenuScreen : Screen {

    // ASSETS
    // - Buttons
    private val PATH = "mainscreen/"
    private val buttonPlayActive =          Texture(PATH + "button-start-active.png")
    private val buttonPlayInactive =        Texture(PATH + "button-start.png")
    private val buttonMultiplayerActive =   Texture(PATH + "button-mp-active.png")
    private val buttonMultiplayerInactive = Texture(PATH + "button-mp.png")
    // - Images
    private val logo =                      Texture(PATH + "logo.png")
    private val background =                Texture(PATH + "background-mainmenu.png")

    private val batch: SpriteBatch = SpriteBatch()
    private val game: Game
    private var stage: Stage
    constructor(game: Game){
        this.game = game
        stage = Stage(ScreenViewport(), batch)
        Gdx.input.inputProcessor = stage
        createUI()
    }

    // DIMENSIONS
    private val h = Gdx.graphics.height.toFloat()
    private val w = Gdx.graphics.width.toFloat()

    private val logoW = w/3
    private val logoH = logo.height * logoW / logo.width
    private val logoPosX = w/2 - logoW/2
    private val logoPosY = h - logoH

    private val buttonplayW = h/3
    private val buttonplayH = buttonPlayInactive.height * buttonplayW / buttonPlayInactive.width
    private val buttonplayPosX = w/2-buttonplayW/2
    private val buttonplayPosY = h/6

    private val buttonmpW = h/3
    private val buttonmpH = buttonMultiplayerInactive.height * buttonmpW / buttonMultiplayerInactive.width
    private val buttonmpPosX = w-buttonmpW-w/30
    private val buttonmpPosY = h/5

    private fun createUI() {
        val root = Table()
        root.setFillParent(true)
        stage.addActor(root)

        val buttonPlay = UIFactory.createImageButton(buttonPlayActive, buttonPlayInactive)
        buttonPlay.setSize(buttonplayW,buttonplayH)
        buttonPlay.setPosition(buttonplayPosX,buttonplayPosY)
        buttonPlay.addListener { _ -> game.screen=LevelSelectScreen(game); Vibration.vibrate(); true }
        stage.addActor(buttonPlay)

        val buttonMP = UIFactory.createImageButton(buttonMultiplayerActive, buttonMultiplayerInactive)
        buttonMP.setSize(buttonmpW, buttonmpH)
        buttonMP.setPosition(buttonmpPosX,buttonmpPosY)
        buttonMP.addListener { _ -> game.screen=MultiplayerLobbyScreen(game); Vibration.vibrate(); true }
        stage.addActor(buttonMP)
    }

    override fun hide() {}

    override fun show() {}

    override fun render(delta: Float) {
        batch.begin()
        // background
        batch.draw(background,0f,0f, w,h)
        batch.draw(logo,
                logoPosX, logoPosY,
                logoW, logoH)
        batch.end()

        stage.act(Gdx.graphics.rawDeltaTime)
        stage.draw()
    }

    override fun pause() {}

    override fun resume() {}

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {
        stage.dispose()
        batch.dispose()

        // TEXTURES
        buttonPlayActive.dispose()
        buttonPlayInactive.dispose()
        buttonMultiplayerActive.dispose()
        buttonMultiplayerInactive.dispose()
        logo.dispose()
        background.dispose()
    }
}