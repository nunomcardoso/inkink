package pt.nunomcards.inkink.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.assetloader.AudioAssets
import pt.nunomcards.inkink.assetloader.LevelAssets
import pt.nunomcards.inkink.gamelogic.LevelLogic
import pt.nunomcards.inkink.gamelogic.SinglePlayerLevelLogic
import pt.nunomcards.inkink.utils.GdxUtils
import pt.nunomcards.inkink.utils.UIFactory

/**
 * Created by nuno on 21/07/2018.
 */
class HUDSinglePlayerEntity(
        level: LevelEntity,
        val logic: SinglePlayerLevelLogic,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : HUDEntity(level, logic, batch, world, camera) {

    private val bomb: Texture
    private val cannon: Texture
    private val bottle: Texture

    private val font: BitmapFont = BitmapFont()

    init {
        bomb = Texture("button-bomb.png")
        cannon = Texture("button-cannon.png")
        bottle = Texture("bottle.png")

        // build Weapon Buttons
        createWeaponButtons()
    }

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
            if(logic.getBombAmmo() > 0){
                // AUDIO
                AudioAssets.bombSound.play()
                level.useBomb()
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
            if(logic.getCannonAmmo() > 0){
                // AUDIO
                AudioAssets.cannonSound.play()
                level.useCannon()
            }
            true
        }
        stage.addActor(cannonBtn)
    }


    private var elapsed = 0f
    override fun render() {
        // PARENT RENDDER
        super.render()

        elapsed+= Gdx.graphics.deltaTime

        batch.begin()
        // TIMER
        font.data.setScale(15f)
        font.draw(batch, logic.getTimer(), GdxUtils.screenW/2-GdxUtils.screenW/20, GdxUtils.screenH)


        // Things to collect
        font.data.setScale(7f)
        val coins = logic.coins
        val maxCoins = logic.maxCoins
        val tiles = logic.tilesPainted
        val maxTiles = logic.maxTilesPainted
        val percentTiles = (tiles.toFloat()/maxTiles.toFloat())*100
        val f = font.draw(batch,"$coins/$maxCoins\n${percentTiles.toInt()}%", GdxUtils.screenW/20, GdxUtils.screenH)
        val sizeIconH = f.height/2*.9f
        val sizeIconW = sizeIconH
        // coin icon
        batch.draw(
                LevelAssets.coinAnim.getKeyFrame(elapsed, true) as TextureRegion,
                0f, GdxUtils.screenH-sizeIconH,
                sizeIconW, sizeIconH)
        // ink icon
        batch.draw(bottle, 0f, GdxUtils.screenH-sizeIconH*2.3f, sizeIconW,sizeIconH)


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

    override fun dispose() {
        font.dispose()
        super.dispose()
    }
}