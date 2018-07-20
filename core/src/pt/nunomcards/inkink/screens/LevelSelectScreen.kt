package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import pt.nunomcards.inkink.assetloader.AudioAssets
import pt.nunomcards.inkink.model.GameMode
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.utils.UIFactory
import pt.nunomcards.inkink.utils.Vibration


/**
 * Created by nuno on 04/07/2018.
 */
class LevelSelectScreen : Screen {

    // ASSETS
    private val PATH_LVL = "level/"
    private val PATH_FONT = "fnts/"
    // - Images
    private val title =          Texture(PATH_FONT + "fnt_levels.png")
    private val background =     Texture("background.png")
    private val button_back =    Texture("button-back.png")

    private val batch = SpriteBatch()
    private val game: Game
    private var stage: Stage
    constructor(game: Game)
    {
        this.game = game
        stage = Stage(ScreenViewport(), batch)
        Gdx.input.inputProcessor = stage
        createUI()
    }


    private fun createUI(){
        val root = Table()
        root.setFillParent(true)
        stage.addActor(root)

        // LVL BUTTONS
        val side = w / 6
        var posX = w / 2 - side / 2 - w/3
        var posY = titlePosY - side - h / 15
        // ROW 1
        var lvlCounter = 1
        for(r in 1..2) {
            for(c in 1..3) {
                val currLvl = lvlCounter
                val btnLvl = UIFactory.createImageButton(Texture(PATH_LVL + "lvl-sp-0$currLvl.png"))
                btnLvl.setSize(side, side)
                btnLvl.setPosition(posX, posY)
                btnLvl.addListener{ _ ->
                    game.screen = LevelScreen(GameMode.SINGLEPLAYER, this.game, PaintColor.values()[currLvl])
                    Vibration.vibrate()
                    true
                 }
                stage.addActor(btnLvl)

                posX += w / 3
                //posY += 0
                lvlCounter+=1
            }
            // reset X & update Y
            posX = w / 2 - side / 2 - w/3
            posY -= side + h/30
        }

        // BACK BUTTON
        val backbutton = UIFactory.createImageButton(button_back)
        backbutton.setSize(side, button_back.height*side/button_back.width)
        backbutton.setPosition(w/60,w/60)
        backbutton.addListener { _ ->
            Vibration.vibrate()
            game.screen= MainMenuScreen(game)
            true
        }
        stage.addActor(backbutton)
    }

    override fun hide() {}

    override fun show() {}

    // DIMENSIONS
    private val h = Gdx.graphics.height.toFloat()
    private val w = Gdx.graphics.width.toFloat()

    private val titleW = w/3
    private val titleH = titleW * title.height / title.width
    private val titlePosX = w/2 - titleW/2
    private val titlePosY = h - titleH

    override fun render(delta: Float) {
        batch.begin()
        // background
        batch.draw(background,0f,0f, w,h)
        batch.draw(title,
                titlePosX, titlePosY-w/30,
                titleW, titleH)
        batch.end()

        stage.act(Gdx.graphics.rawDeltaTime)
        stage.draw()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {
        // TEXTURES
        title.dispose()
        background.dispose()
        button_back.dispose()
    }
}