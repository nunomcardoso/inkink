package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
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

        val chest = Texture("chest_sprite.png")
        drawIsometric(4,4, chest)
        val player = Texture("player.png")
        drawIsometric(4,0, player)

        batch.end()

        stage.act(Gdx.graphics.rawDeltaTime)
        stage.draw()
    }

    private fun drawIsometric(c: Int, r: Int, texture: Texture){
        val txtW = tileSizeW / 2
        val txtH = txtW * (texture.height / texture.width)
        val coords = twoDtoIso(r,c)
        coords.x += txtW/2
        coords.y += txtH/4      // so the sprite is centered
        batch.draw(texture, coords.x, coords.y, txtW, txtH)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {

    }

    // Works fine for Square arenas
    private val arenaCols = 10
    private val arenaRows = arenaCols
    private val scaleFactor = .9f
    private val tileSizeW = w/arenaRows * scaleFactor
    private val tileSizeH = tileSizeW * (tile.height.toFloat() / tile.width.toFloat())
    private val isoX = w/2 - tileSizeW/2
    private val isoY = h - (h-arenaRows*tileSizeH)/2 - tileSizeH*2
    private fun renderArena(){
        var curX = isoX
        var curY = isoY
        for(r in 1..arenaRows){
            for(c in 1..arenaCols) {
                // TODO check color
                batch.draw(tile, curX, curY, tileSizeW, tileSizeH)
                curX += tileSizeW/2
                curY -= tileSizeH/2
            }
            curX = isoX - tileSizeW/2*r
            curY = isoY - tileSizeH/2*r
        }
    }

    // row and col start at 0
    private fun twoDtoIso(r: Int, c: Int): Vector2{
        var xx = isoX
        var yy = isoY

        // SELECT ROW
        xx -= tileSizeW/2*r
        yy -= tileSizeH/2*r
        // SELECT COLUMN
        xx += tileSizeW/2*c
        yy -= tileSizeH/2*c

        return Vector2(xx,yy)
    }
}