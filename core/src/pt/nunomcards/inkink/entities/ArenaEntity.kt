package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.EdgeShape
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.model.Arena
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.utils.GdxUtils.Companion.screenH
import pt.nunomcards.inkink.utils.GdxUtils.Companion.screenW
import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 13/07/2018.
 */

/**
 * THIS IS ONLY FOR SQUARE ARENAS
 * RECTANGULAR ARENAS (rows!=cols) DONT WORK LIKE THEY SHOULD
 */
class ArenaEntity(
        private val rows: Int,
        private val cols: Int,
        private val batch: SpriteBatch,
        private val world: World,
        private val camera: OrthographicCamera
) : BaseEntity{

    private val arena = Arena(rows,cols)
    private val tile = Texture("level/tile-flat.png")

    // Works fine for Square arenas
    private val scaleFactor = .9f
    val tileSizeW = screenW/rows * scaleFactor
    val tileSizeH = tileSizeW * (tile.height.toFloat() / tile.width.toFloat())

    // Start point where the arena will be built
    private val isoX = screenW/2 - tileSizeW/2
    private val isoY = screenH - (screenH-rows*tileSizeH)/2 - tileSizeH*2

    override fun render() {
        //
        // RENDER
        //
        var curX = isoX
        var curY = isoY
        for(r in 1..rows){
            for(c in 1..cols) {
                batch.draw(
                        getTileColor(arena.map[c][r].color),
                        curX, curY,
                        tileSizeW, tileSizeH
                )
                curX += tileSizeW/2
                curY -= tileSizeH/2
            }
            curX = isoX - tileSizeW/2*r
            curY = isoY - tileSizeH/2*r
        }

        // RENDER TILE OBJECTS

    }

    private fun renderArenaLimitationsBox2D(){
        // TODO: this measures are not perfect, work "fine" in 16:9 and 4:3 screens

        // DIVIDIR por PPM
        // Assim consegue-se melhores medidas e angulos

        val cvpH  = camera.viewportHeight
        val cvpW = camera.viewportWidth
        //vertical offset
        val offset = -1f
        val limlength = cols*3

        val coords = arrayOf(
            // LEFT limits
            Triple(Vector2(0f,cvpH/2+offset),
                    Vector2(0f,0f), Vector2(1f*limlength,0.5f*limlength)),
            Triple(Vector2(0f,cvpH/2+offset),
                    Vector2(0f,0f), Vector2(1f*limlength,-0.5f*limlength)),
            // RIGHT limits
            Triple(Vector2(cvpW,cvpH/2+offset),
                    Vector2(0f,0f), Vector2(-1f*limlength,-0.5f*limlength)),
            Triple(Vector2(cvpW,cvpH/2+offset),
                    Vector2(0f,0f), Vector2(-1f*limlength,0.5f*limlength))
        )

        for(cd in coords) {
            val limits = BodyDef()
            limits.position.set(cd.first)
            val limitBody = world.createBody(limits)
            val limitBox = EdgeShape()
            limitBox.set(cd.second, cd.third)
            limitBody.createFixture(limitBox, 0.0f)
        }
    }


    fun colorTile(row: Int, col: Int, color: PaintColor){
        arena.map[row][col].color = color
    }

    fun useBomb(row: Int, col: Int){
        arena.placeBombInk(row,col)
    }

    fun useCannon(row: Int, col: Int){
        arena.shootCannonInk(row,col)
    }

    // to draw textures on the arena
    fun drawIsometric(c: Int, r: Int, texture: Texture){
        val txtW = tileSizeW / 2
        val txtH = txtW * (texture.height / texture.width)
        val coords = twoDtoIso(IsometricCoords(r,c))

        coords.x += txtW/2
        coords.y += txtH/4 // so the sprite is centered
        batch.draw(
                texture,
                coords.x, coords.y,
                txtW, txtH
        )
    }

    // row and col start at 0
    fun twoDtoIso(coords: IsometricCoords): Vector2{
        var xx = isoX
        var yy = isoY

        // SELECT ROW
        xx -= tileSizeW/2 * coords.row
        yy -= tileSizeH/2 * coords.row
        // SELECT COLUMN
        xx += tileSizeW/2 * coords.col
        yy -= tileSizeH/2 * coords.col

        return Vector2(xx,yy)
    }

    fun deviceToIso(x: Int, y: Int): IsometricCoords{
        TODO("make the algorithm")
        return IsometricCoords(x, y)
    }

    private fun getTileColor(color: PaintColor): TextureRegion {
        TODO("create the other colors")
        return TextureRegion(tile)
    }
}