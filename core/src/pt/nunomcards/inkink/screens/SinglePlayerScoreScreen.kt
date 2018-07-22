package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pt.nunomcards.inkink.assetloader.LevelAssets
import pt.nunomcards.inkink.utils.GdxUtils
import pt.nunomcards.inkink.utils.UIFactory
import pt.nunomcards.inkink.utils.Vibration

/**
 * Created by nuno on 21/07/2018.
 */
class SinglePlayerScoreScreen : Screen {
    private val PATH_FONT = "fnts/"
    private val LVL_FONT = "level/"

    // - Images
    private val title =             Texture(PATH_FONT + "fnt-score.png")
    private val background =        Texture("background.png")
    private val button_back =       Texture("button-back.png")

    private val tile =        Texture(LVL_FONT + "tile-flat.png")
    private val clock =        Texture("clock.png")

    private val font: BitmapFont = BitmapFont()

    private val batch = SpriteBatch()
    private val game: Game
    private var stage: Stage

    private val coins: Pair<Int, Int>
    private val timeLeft: Int
    private val tilePercentage: Int

    constructor(game: Game, coins: Pair<Int, Int>, tilePercentage: Int, timeLeft: Int)
    {
        this.game = game
        this.coins = coins
        this.timeLeft = timeLeft
        this.tilePercentage = tilePercentage

        stage = Stage(ScreenViewport(), batch)
        Gdx.input.inputProcessor = stage
        createUI()
    }

    fun createUI() {
        val side = GdxUtils.screenW / 6

        // BACK BUTTON
        val backbutton = UIFactory.createImageButton(button_back)
        backbutton.setSize(side, button_back.height*side/button_back.width)
        backbutton.setPosition(GdxUtils.screenW/60,GdxUtils.screenW/60)
        backbutton.addListener { _ ->
            Vibration.vibrate()
            game.screen= MainMenuScreen(game)
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

    private var elapsed = 0f
    override fun render(delta: Float) {
        elapsed += Gdx.graphics.deltaTime

        batch.begin()
        // background
        batch.draw(background,0f,0f, GdxUtils.screenW,GdxUtils.screenH)
        batch.draw(title,
                titlePosX, titlePosY-GdxUtils.screenW/30,
                titleW, titleH)

        font.data.setScale(15f)
        val initPos = GdxUtils.screenH - GdxUtils.screenH*0.3f

        val f = font.draw(batch, "${coins.first}/${coins.second}", GdxUtils.screenW/2, initPos)
        font.draw(batch, "$tilePercentage%", GdxUtils.screenW/2, initPos-GdxUtils.screenH*.2f)
        font.draw(batch, "$timeLeft sec left", GdxUtils.screenW/2, initPos-GdxUtils.screenH*.4f)

        batch.draw(LevelAssets.coinAnim.getKeyFrame(elapsed,true) as TextureRegion,GdxUtils.screenW/2*0.8f, initPos-f.height, f.height, f.height)
        batch.draw(tile, GdxUtils.screenW/2*.8f, initPos-GdxUtils.screenH*.2f-f.height, f.height, f.height/2)
        batch.draw(clock, GdxUtils.screenW/2*.8f, initPos-GdxUtils.screenH*.4f-f.height, f.height, f.height)

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