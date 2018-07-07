package pt.nunomcards.inkink

import com.badlogic.gdx.Game
import pt.nunomcards.inkink.screens.MainMenuScreen

/**
 * Created by nuno on 04/07/2018.
 */
class InkInkGame : Game() {
    override fun create() {
        screen = MainMenuScreen(this)
    }

    override fun dispose() {
        super.dispose()
    }
}