package pt.nunomcards.inkink.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.assetloader.PlayerAssetLoader
import pt.nunomcards.inkink.utils.GdxUtils
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import pt.nunomcards.inkink.assetloader.AudioAssets
import pt.nunomcards.inkink.gamelogic.MultiPlayerLevelLogic
import pt.nunomcards.inkink.model.Weapon
import pt.nunomcards.inkink.multiplayer.MultiplayerHandler
import pt.nunomcards.inkink.utils.UIFactory

/**
 * Created by nuno on 21/07/2018.
 */
class HUDMultiPlayerEntity(
        level: LevelEntity,
        val logic: MultiPlayerLevelLogic,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : HUDEntity(level, logic, batch, world, camera) {

    private val font: BitmapFont = BitmapFont()
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

    private val bomb: Texture
    private val cannon: Texture
    private val bottle: Texture

    init {
        bomb = Texture("button-bomb.png")
        cannon = Texture("button-cannon.png")
        bottle = Texture("bottle.png")

        // build Weapon Buttons
        createWeaponButtons()
    }

    override fun render() {
        // PARENT RENDER
        super.render()

        batch.begin()
        // TIMER
        font.data.setScale(15f)
        font.draw(batch,logic.getTimer().toString(), GdxUtils.screenW/2- GdxUtils.screenW/20, GdxUtils.screenH)

        // Percentage Meter
        font.data.setScale(3f)
        val sizeIcon = GdxUtils.screenW/32
        var coordX = sizeIcon
        val coordY = GdxUtils.screenH - sizeIcon*2
        val xOffset = sizeIcon*.7f
        for(c in 0 until colors.size) {
            font.color = colors[c]
            font.draw(batch, "${logic.percentages[c]}%", coordX, coordY)

            // coin icon
            batch.draw(
                    iconTextures[c],
                    coordX+sizeIcon*.1f, coordY,
                    sizeIcon, sizeIcon)
            coordX+=sizeIcon+xOffset
        }

        font.color = Color.WHITE

        // Ammo left
        font.data.setScale(4f)
        val dpad_side = GdxUtils.screenW/5 / 3f
        val borderOffset = GdxUtils.screenW/60
        val bomb_posX = GdxUtils.screenW - dpad_side*1.25f
        val bomb_posY = borderOffset*2
        font.draw(batch,"${logic.getBombAmmo()}x", bomb_posX, bomb_posY)
        font.draw(batch,"${logic.getCannonAmmo()}x", bomb_posX-dpad_side*2.1f, bomb_posY)

        batch.end()
    }

    var lastimePressedBomb = System.currentTimeMillis()
    var lastimePressedCannon = System.currentTimeMillis()
    private fun createWeaponButtons() {
        val dpad_side = GdxUtils.screenW/5 / 3f
        val borderOffset = GdxUtils.screenW/60

        val bomb_posX = GdxUtils.screenW - dpad_side*2
        val bomb_posY = borderOffset

        // BUTTONS
        // BOMB
        val bombBtn = UIFactory.createImageButton(bomb)
        bombBtn.setSize(dpad_side,dpad_side)
        bombBtn.image.setFillParent(true)
        bombBtn.setPosition(bomb_posX, bomb_posY)
        bombBtn.addListener { _ ->
            if(System.currentTimeMillis() > lastimePressedBomb+500 && logic.canUseBomb() ){
                MultiplayerHandler.useWeapon(player.player.team, player.player.coordsIso, Weapon.WeaponType.BOMB)
                lastimePressedBomb = System.currentTimeMillis()
                // AUDIO
                AudioAssets.bombSound.play()
                Gdx.input.vibrate(longArrayOf(300, 50, 50, 300),-1)
            }
            true
        }
        stage.addActor(bombBtn)

        //CANNON
        val cannonBtn = UIFactory.createImageButton(cannon)
        cannonBtn.setSize(dpad_side,dpad_side)
        cannonBtn.image.setFillParent(true)
        cannonBtn.setPosition(bomb_posX-dpad_side*2.1f, bomb_posY)
        cannonBtn.addListener { _ ->
            if(System.currentTimeMillis() > lastimePressedCannon+500 && logic.canUseCannon()){
                MultiplayerHandler.useWeapon(player.player.team, player.player.coordsIso, Weapon.WeaponType.CANNON)
                lastimePressedCannon = System.currentTimeMillis()
                // AUDIO
                AudioAssets.cannonSound.play()
                Gdx.input.vibrate(longArrayOf(150, 0, 0, 150),-1)
            }
            true
        }
        stage.addActor(cannonBtn)
    }
}