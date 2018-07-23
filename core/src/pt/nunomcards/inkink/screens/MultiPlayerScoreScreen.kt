package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pt.nunomcards.inkink.assetloader.AudioAssets
import pt.nunomcards.inkink.assetloader.PlayerAssetLoader
import pt.nunomcards.inkink.multiplayer.MultiplayerHandler
import pt.nunomcards.inkink.utils.GdxUtils
import pt.nunomcards.inkink.utils.UIFactory
import pt.nunomcards.inkink.utils.Vibration

/**
 * Created by nuno on 21/07/2018.
 */
class MultiPlayerScoreScreen : Screen {
    private val PATH_FONT = "fnts/"
    private val LVL_FONT = "level/"

    // - Images
    private val title =             Texture(PATH_FONT + "fnt-score.png")
    private val background =        Texture("background.png")
    private val button_back =       Texture("button-back.png")

    private val batch = SpriteBatch()
    private val game: Game
    private var stage: Stage

    private val colors =
            arrayOf(
                    Color.RED,
                    Color.ORANGE,
                    Color.YELLOW,
                    Color.GREEN,
                    Color.ROYAL,
                    Color.PURPLE
            )
    private val iconTextures =
            arrayOf(
                    PlayerAssetLoader.playerRed[0],
                    PlayerAssetLoader.playerOrange[0],
                    PlayerAssetLoader.playerYellow[0],
                    PlayerAssetLoader.playerGreen[0],
                    PlayerAssetLoader.playerBlue[0],
                    PlayerAssetLoader.playerPurple[0]
            )

    private val font: BitmapFont = BitmapFont()

    private val percentages: IntArray

    constructor(game: Game, percentages: IntArray)
    {
        MultiplayerHandler.end()

        this.game = game
        this.percentages = percentages

        stage = Stage(ScreenViewport(), batch)
        Gdx.input.inputProcessor = stage
        createUI()

        // AUDIO
        AudioAssets.endLevel.play()

        createUI()
    }

    private fun createUI() {
        val side = GdxUtils.screenW / 6

        // BACK BUTTON
        val backbutton = UIFactory.createImageButton(button_back)
        backbutton.setSize(side, button_back.height*side/button_back.width)
        backbutton.setPosition(GdxUtils.screenW/2-side/2,GdxUtils.screenW/60)
        backbutton.addListener { _ ->
            // AUDIO
            AudioAssets.selectSound.play()

            Vibration.vibrate()
            game.screen= MultiplayerLobbyScreen(game)
            true
        }
        stage.addActor(backbutton)
    }


    override fun hide() {}

    override fun show() {}

    private val titleW = GdxUtils.screenW/3
    private val titleH = title.height * titleW / title.width
    private val titlePosX = GdxUtils.screenW/2 - titleW/2
    private val titlePosY = GdxUtils.screenH - titleH

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        // background
        batch.draw(background,0f,0f, GdxUtils.screenW,GdxUtils.screenH)
        batch.draw(title,
                titlePosX, titlePosY-GdxUtils.screenW/30,
                titleW, titleH)


        // Percentage Meter
        font.data.setScale(8f)
        val sizeIcon = GdxUtils.screenW/10
        var coordX = 0f
        val coordY = GdxUtils.screenH - sizeIcon*3.5f
        val xOffset = sizeIcon*.7f
        for(c in 0 until percentages.size) {
            font.color = colors[c]
            font.draw(batch, "${percentages[c]}%", coordX, coordY)

            // coin icon
            batch.draw(
                    iconTextures[c],
                    coordX, coordY,
                    sizeIcon, sizeIcon)
            coordX+=sizeIcon+xOffset
        }

        batch.end()

        stage.act(Gdx.graphics.rawDeltaTime)
        stage.draw()
    }

    override fun pause() {}

    override fun resume() {}

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {
    }
}