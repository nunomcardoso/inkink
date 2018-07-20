package pt.nunomcards.inkink.assetloader

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import pt.nunomcards.inkink.model.PaintColor
import java.io.Serializable
import java.util.*

/**
 * Created by nuno on 19/07/2018.
 */
class PlayerAssetLoaderKT {

    val playerWhite=    LinkedList<TextureRegion>()
    val playerRed=      LinkedList<TextureRegion>()
    val playerOrange=   LinkedList<TextureRegion>()
    val playerYellow=   LinkedList<TextureRegion>()
    val playerGreen=    LinkedList<TextureRegion>()
    val playerBlue=     LinkedList<TextureRegion>()
    val playerPurple=   LinkedList<TextureRegion>()

    //val playerWhiteAnim:    Animation<TextureRegion>
    //val playerRedAnim:      Animation<TextureRegion>
    //val playerOrangeAnim:   Animation<TextureRegion>
    //val playerYellowAnim:   Animation<TextureRegion>
    //val playerGreenAnim:    Animation<TextureRegion>
    //val playerBlueAnim:     Animation<TextureRegion>
    //val playerPurpleAnim:   Animation<TextureRegion>

    init{
        val texturePlayers = Texture("player_sprite.png")
        val step = 16 //pixels
        val frameDuration = 0.5f

        for(color in 0..7)
            for(pos in 0..3){
                val texture = TextureRegion(texturePlayers,step*pos,step*color, step,step)
                when(color){
                    0 -> playerWhite.add(texture)
                    1 -> playerRed.add(texture)
                    2 -> playerOrange.add(texture)
                    3 -> playerYellow.add(texture)
                    4 -> playerGreen.add(texture)
                    5 -> playerBlue.add(texture)
                    6 -> playerPurple.add(texture)
                }
            }

        // Animation Creator
        /*playerWhiteAnim=    Animation(frameDuration, playerWhite.toArray()), Animation.GameMode.NORMAL)
        playerRedAnim=      Animation(frameDuration, playerRed.toArray(), Animation.GameMode.NORMAL)
        playerOrangeAnim=   Animation(frameDuration, playerOrange.toArray(), Animation.GameMode.NORMAL)
        playerYellowAnim=   Animation(frameDuration, playerYellow.toArray(), Animation.GameMode.NORMAL)
        playerGreenAnim=    Animation(frameDuration, playerGreen.toArray(), Animation.GameMode.NORMAL)
        playerBlueAnim=     Animation(frameDuration, playerBlue.toArray(), Animation.GameMode.NORMAL)
        playerPurpleAnim=   Animation(frameDuration, playerPurple.toArray(), Animation.GameMode.NORMAL)
        */
    }

    //fun getKeyFrameTexture(color: PaintColor, elapsed: Float): TextureRegion{
    //    return when(color){
    //        PaintColor.WHITE    -> playerWhiteAnim.getKeyFrame(elapsed, true)      as TextureRegion
    //        PaintColor.RED      -> playerRedAnim.getKeyFrame(elapsed, true)        as TextureRegion
    //        PaintColor.ORANGE   -> playerOrangeAnim.getKeyFrame(elapsed, true)     as TextureRegion
    //        PaintColor.YELLOW   -> playerYellowAnim.getKeyFrame(elapsed, true)     as TextureRegion
    //        PaintColor.GREEN    -> playerGreenAnim.getKeyFrame(elapsed, true)      as TextureRegion
    //        PaintColor.BLUE     -> playerBlueAnim.getKeyFrame(elapsed, true)       as TextureRegion
    //        PaintColor.PURPLE   -> playerPurpleAnim.getKeyFrame(elapsed, true)     as TextureRegion
    //    }
    //}

    fun dispose(){

    }
}