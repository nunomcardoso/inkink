package pt.nunomcards.inkink.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import pt.nunomcards.inkink.gameinput.InputHandler
import pt.nunomcards.inkink.gameinput.Movement
import pt.nunomcards.inkink.utils.UIFactory
import pt.nunomcards.inkink.utils.Vibration

/**
 * Created by nuno on 13/07/2018.
 */
/**
 * Head-Up Display (HUD)
 * "is the method by which information is visually relayed to the player as part of a game's user interface" - wikipedia
 */
class HUDEntity(
        val player: PlayerEntity,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : BaseEntity(batch, world, camera) {

    val stage: Stage

    // DPad Arrows
    private val arrowTextureR: Texture
    private val arrowTextureL: Texture
    private val arrowTextureU: Texture
    private val arrowTextureD: Texture

    init{
        stage = Gdx.input.inputProcessor as Stage
        arrowTextureR = Texture("arrowRight.png")
        arrowTextureL = Texture("arrowLeft.png")
        arrowTextureU = Texture("arrowUp.png")
        arrowTextureD = Texture("arrowDown.png")

        // build DPad
        createDPad()
    }

    override fun render() {
        InputHandler.movePlayer(player)
    }

    /**
     * CREATES THE DIRECTIONAL PAD
     * EACH BUTTON MAKES THE CHARACTER MOVE
     */
    fun createDPad(){

        val dpad_side = Gdx.graphics.width.toFloat()/5 / 3f
        val borderOffset = Gdx.graphics.width/60

        // BUTTON POSITION
        val r_posX = borderOffset + 2 * dpad_side
        val r_posY = borderOffset + dpad_side

        val l_posX = borderOffset + 0f
        val l_posY = borderOffset +dpad_side

        val u_posX = borderOffset +dpad_side
        val u_posY = borderOffset +2 * dpad_side

        val d_posX = borderOffset +dpad_side
        val d_posY = borderOffset +0f

        // BUTTONS
        val dpadRight = UIFactory.createImageButton(arrowTextureR)
        dpadRight.setSize(dpad_side,dpad_side)
        dpadRight.setPosition(r_posX,r_posY)
        dpadRight.addListener { _ ->
            InputHandler.movePlayer(player, Movement.RIGHT)
            Vibration.vibrate(10)
            true
        }
        stage.addActor(dpadRight)

        val dpadLeft= UIFactory.createImageButton(arrowTextureL)
        dpadLeft.setSize(dpad_side,dpad_side)
        dpadLeft.setPosition(l_posX,l_posY)
        dpadLeft.addListener { _ ->
            InputHandler.movePlayer(player, Movement.LEFT)
            Vibration.vibrate(10)
            true
        }
        stage.addActor(dpadLeft)

        val dpadUp= UIFactory.createImageButton(arrowTextureU)
        dpadUp.setSize(dpad_side,dpad_side)
        dpadUp.setPosition(u_posX,u_posY)
        dpadUp.addListener { _ ->
            InputHandler.movePlayer(player, Movement.UP)
            Vibration.vibrate(10)
            true
        }
        stage.addActor(dpadUp)

        val dpadDown= UIFactory.createImageButton(arrowTextureD)
        dpadDown.setSize(dpad_side,dpad_side)
        dpadDown.setPosition(d_posX,d_posY)
        dpadDown.addListener { _ ->
            InputHandler.movePlayer(player, Movement.DOWN)
            Vibration.vibrate(10)
            true
        }
        stage.addActor(dpadDown)
    }

    override fun dispose() {
        stage.dispose()
    }
}