package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.assetloader.PlayerAssetLoader
import pt.nunomcards.inkink.utils.GdxUtils
import com.badlogic.gdx.graphics.Color
import pt.nunomcards.inkink.gamelogic.MultiPlayerLevelLogic

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

    override fun render() {
        // PARENT RENDER
        super.render()

        batch.begin()
        // TIMER
        font.color = Color.WHITE
        font.data.setScale(15f)
        font.draw(batch,"00", GdxUtils.screenW/2- GdxUtils.screenW/20, GdxUtils.screenH)

        // Things to collect
        font.data.setScale(3f)
        val sizeIcon = GdxUtils.screenW/32
        var coordX = sizeIcon
        val coordY = GdxUtils.screenH - sizeIcon*2
        val xOffset = sizeIcon*.7f
        for(c in 0 until colors.size) {
            font.color = colors[c]
            val f = font.draw(batch, "0%", coordX, coordY) // TODO change percentage

            // coin icon
            batch.draw(
                    iconTextures[c],
                    coordX+sizeIcon*.1f, coordY,
                    sizeIcon, sizeIcon)
            coordX+=sizeIcon+xOffset
        }
        batch.end()
    }
}