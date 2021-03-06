package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pt.nunomcards.inkink.assetloader.AudioAssets
import pt.nunomcards.inkink.gamelogic.LevelGenerator
import pt.nunomcards.inkink.model.GameMode
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.utils.UIFactory
import pt.nunomcards.inkink.utils.Vibration
import com.sun.awt.SecurityWarning.setPosition
import com.badlogic.gdx.assets.loaders.AssetLoader
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import pt.nunomcards.inkink.assetloader.SkinAssets
import pt.nunomcards.inkink.utils.GdxUtils


/**
 * Created by nuno on 04/07/2018.
 */
class MultiplayerLobbyScreen : Screen {
    // ASSETS
    private val PATH_MP = "mplobby/"
    private val PATH_FONT = "fnts/"

    private val teamRed =           Texture(PATH_MP + "choose-team-red.png")
    private val teamOrange =        Texture(PATH_MP + "choose-team-orange.png")
    private val teamYellow =        Texture(PATH_MP + "choose-team-yellow.png")
    private val teamGreen =         Texture(PATH_MP + "choose-team-green.png")
    private val teamBlue =          Texture(PATH_MP + "choose-team-blue.png")
    private val teamPurple =        Texture(PATH_MP + "choose-team-purple.png")
    // - Images
    private val title =             Texture(PATH_FONT + "fnt_multiplayer.png")
    private val background =        Texture("background.png")
    private val button_back =       Texture("button-back.png")
    private val check =             Texture("check.png")

    private val buttonPlay =        Texture("mainscreen/button-start.png")


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

    var teamChosen = PaintColor.WHITE
    private fun createUI() {
        val root = Table()
        root.setFillParent(true)
        stage.addActor(root)

        val btnArr = arrayOf(teamRed, teamOrange, teamYellow, teamGreen, teamBlue, teamPurple)

        // LVL BUTTONS
        val side = w / 6
        var posX = w / 2 - side / 2 - w/3
        var posY = titlePosY - side - h / 8
        var counter = 0
        for(r in 1..2) {
            for(c in 1..3) {
                val btnLvl = UIFactory.createImageButton(btnArr[counter])
                btnLvl.setSize(side, side)
                btnLvl.setPosition(posX, posY)
                btnLvl.image.setFillParent(true)
                val color = counter
                btnLvl.addListener{ _ ->
                    Vibration.vibrate()
                    // CHANGE SCREEN
                    teamChosen = PaintColor.values()[color+1]
                    true
                }
                stage.addActor(btnLvl)

                posX += w / 3
                //posY += 0
                counter+=1
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
            // AUDIO
            AudioAssets.selectSound.play()

            Vibration.vibrate()
            game.screen= MainMenuScreen(game)
            true
        }
        stage.addActor(backbutton)

        // Start BUTTON
        val startButton = UIFactory.createImageButton(buttonPlay)
        startButton.setSize(side, button_back.height*side/button_back.width)
        startButton.setPosition(GdxUtils.screenW/2-side/2, GdxUtils.screenW/60)
        startButton.addListener { _ ->
            // AUDIO
            AudioAssets.selectSound.play()

            Vibration.vibrate()
            if(teamChosen != PaintColor.WHITE)
                game.screen = LevelScreen(game, LevelGenerator.getMultiPlayerLevel(teamChosen))

            true
        }
        stage.addActor(startButton)

        val usernameTextField = TextField("", SkinAssets.skin)
        usernameTextField.setPosition(24f, 73f)
        usernameTextField.setSize(88f, 14f)
        stage.addActor(usernameTextField)
    }

    override fun hide() {}

    override fun show() {}

    // DIMENSIONS
    private val h = Gdx.graphics.height.toFloat()
    private val w = Gdx.graphics.width.toFloat()

    private val titleW = w/3
    private val titleH = title.height * titleW / title.width
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

    override fun pause() {}

    override fun resume() {}

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {
        // Textures
        teamRed.dispose()
        teamOrange.dispose()
        teamYellow.dispose()
        teamGreen.dispose()
        teamBlue.dispose()
        teamPurple.dispose()
        title.dispose()
        background.dispose()
        button_back.dispose()
        check.dispose()
    }
}