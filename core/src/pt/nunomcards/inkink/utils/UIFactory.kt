package pt.nunomcards.inkink.utils

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * Created by nuno on 06/07/2018.
 */
class UIFactory {
    companion object {
        fun createImageButton(texture: Texture): ImageButton{
            return ImageButton(TextureRegionDrawable(TextureRegion(texture)))
        }

        fun createImageButton(textureDown: Texture, textureUp: Texture): ImageButton{
            return ImageButton(TextureRegionDrawable(TextureRegion(textureUp)), TextureRegionDrawable(TextureRegion(textureDown)))
        }
    }
}