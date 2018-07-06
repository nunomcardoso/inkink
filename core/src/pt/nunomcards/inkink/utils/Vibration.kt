package pt.nunomcards.inkink.utils

import com.badlogic.gdx.Gdx

/**
 * Created by nuno on 06/07/2018.
 */
class Vibration {
    companion object {
        // Default
        fun vibrate(){
            Gdx.input.vibrate(longArrayOf(0, 50, 0),-1)
        }

        fun vibrate(milis: Int){
            Gdx.input.vibrate(milis)
        }
    }
}