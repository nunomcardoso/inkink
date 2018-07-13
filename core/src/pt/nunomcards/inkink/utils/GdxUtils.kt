package pt.nunomcards.inkink.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

/**
 * Created by nuno on 13/07/2018.
 */
class GdxUtils {
    companion object {

        val PPM = 100f

        val BOX_STEP = 1 / 60f
        val BOX_VELOCITY_ITERATIONS = 6
        val BOX_POSITION_ITERATIONS = 2


        // DIMENSIONS
        val screenH = Gdx.graphics.height.toFloat()
        val screenW = Gdx.graphics.width.toFloat()

        /**
         * Pair.first = Height
         * Pair.second = Width
         */
        fun coordsBySize(desiredSize: Float, texture: Texture): Pair<Float,Float>{
            return Pair(
                    desiredSize*(texture.height / texture.width),
                    desiredSize
            )
        }
    }
}