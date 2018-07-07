package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport

/**
 * Created by nuno on 04/07/2018.
 */
class LevelScreen : Screen {
    private val tile = Texture("level/tile-flat.png")


    private val batch = SpriteBatch()
    private val game: Game
    private var stage: Stage
    constructor(game: Game, lvl: Int) {
        this.game = game
        stage = Stage(ScreenViewport(), batch)
        Gdx.input.inputProcessor = stage
    }

    override fun hide() {}

    override fun show() {}

    // DIMENSIONS
    private val h = Gdx.graphics.height.toFloat()
    private val w = Gdx.graphics.width.toFloat()
    override fun render(delta: Float) {
        batch.begin()
        renderArena()
        batch.end()

        stage.act(Gdx.graphics.rawDeltaTime)
        stage.draw()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {

    }

    // Works fine for Square arenas
    val arenaCols = 40
    val arenaRows = arenaCols
    private fun renderArena(){
        val tileSizeW = w/arenaRows
        val tileSizeH = tileSizeW * (tile.height.toFloat() / tile.width.toFloat())
        val isoX = w/2 - tileSizeW/2
        val isoY = h - (h-arenaRows*tileSizeH)/2 - tileSizeH

        var curX = isoX
        var curY = isoY
        for(r in 1..arenaRows){
            for(c in 1..arenaCols) {
                batch.draw(tile, curX, curY, tileSizeW, tileSizeH)
                curX += tileSizeW/2
                curY -= tileSizeH/2
            }
            curX = isoX - tileSizeW/2*r
            curY = isoY - tileSizeH/2*r
        }
    }
}