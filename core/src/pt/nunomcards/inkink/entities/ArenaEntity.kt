package pt.nunomcards.inkink.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.EdgeShape
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.assetloader.LevelAssets
import pt.nunomcards.inkink.model.Arena
import pt.nunomcards.inkink.model.ObjectType
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.multiplayer.MultiplayerHandler
import pt.nunomcards.inkink.utils.CartesianCoords
import pt.nunomcards.inkink.utils.GdxUtils.Companion.screenH
import pt.nunomcards.inkink.utils.GdxUtils.Companion.screenW
import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 13/07/2018.
 */

/**
 * THIS IS ONLY FOR SQUARE ARENAS
 * RECTANGULAR ARENAS (rows!=cols) DOESNT WORK LIKE IT SHOULD
 */
class ArenaEntity(
        private val arena: Arena,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : BaseEntity(batch, world, camera) {

    private val rows = arena.rows
    private val cols = arena.columns
    private val tile  = Texture("level/tile-flat.png")
    private val tileR = Texture("level/tile-flat-red.png")
    private val tileO = Texture("level/tile-flat-orange.png")
    private val tileY = Texture("level/tile-flat-yellow.png")
    private val tileG = Texture("level/tile-flat-green.png")
    private val tileB = Texture("level/tile-flat-blue.png")
    private val tileP = Texture("level/tile-flat-purple.png")

    // Works fine for Square arenas
    private val scaleFactor = .9f
    val tileSizeW = screenW/rows * scaleFactor
    val tileSizeH = tileSizeW * (tile.height.toFloat() / tile.width.toFloat())

    // Start point where the arenaEntity will be built
    val isoX = screenW/2 - tileSizeW/2
    val isoY = screenH/2 + tileSizeH*rows/2 - tileSizeH*1.5f

    init{
        // Box 2D Limits
        renderArenaLimitationsBox2D()
    }

    var elapsed = 0f
    override fun render() {
        elapsed += Gdx.graphics.deltaTime

        batch.begin()

        //
        // RENDER Arena
        //
        for(r in 0 until rows){
            for(c in 0 until cols) {
                var color = arena.map[r][c].color
                val coordsCart = twoDtoIso(IsometricCoords(r,c))
                batch.draw(
                        getTileColor(color),
                        coordsCart.x, coordsCart.y,
                        tileSizeW, tileSizeH
                )
            }
        }

        //
        // RENDER OBJECTS
        //
        for(o in arena.tileObjs){
            val texture = when(o.obj){
                ObjectType.CHEST_BOMB -> TextureRegion(LevelAssets.chest)
                ObjectType.CHEST_CANNON -> TextureRegion(LevelAssets.chest)
                ObjectType.FINAL_PLATFORM -> TextureRegion(LevelAssets.teleport)
                ObjectType.COIN -> LevelAssets.coinAnim.getKeyFrame(elapsed, true) as TextureRegion
            }
            drawIsometric(o.isometricCoords.row ,o.isometricCoords.col, texture)
        }
        batch.end()
    }

    // Just needs to be "rendered" 1 time
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
        if(row > rows || row<0 || col>cols || col<0)
            return
        arena.map[row][col].color = color

        tryUpdateMultiplayer(row,col,color)
    }

    private fun tryUpdateMultiplayer(row: Int, col: Int, color: PaintColor){
        MultiplayerHandler.paintTile(color, IsometricCoords(row,col))
    }

    fun useBomb(row: Int, col: Int, color: PaintColor){
        val playerPosition = IsometricCoords(row,col)
        arena.placeBombInk(playerPosition, color)
    }

    fun useCannon(row: Int, col: Int, color: PaintColor){
        val playerPosition = IsometricCoords(row,col)
        arena.shootCannonInk(playerPosition, color)
    }

    //
    //to draw textures on the arenaEntity
    private fun drawIsometric(r: Int, c: Int, texture: Texture){
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
    private fun drawIsometric(r: Int, c: Int, texture: TextureRegion){
        val txtW = tileSizeW / 2
        val txtH = txtW * (texture.regionHeight / texture.regionWidth)
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
    // Vector2 is the bottom left corner of the rectangle that limits the tile
    //     ┌───┐
    //  -> └───┘
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

    private fun getTileColor(color: PaintColor): TextureRegion {
        return when(color){
            PaintColor.WHITE        -> TextureRegion(tile)
            PaintColor.RED          -> TextureRegion(tileR)
            PaintColor.ORANGE       -> TextureRegion(tileO)
            PaintColor.YELLOW       -> TextureRegion(tileY)
            PaintColor.GREEN        -> TextureRegion(tileG)
            PaintColor.BLUE         -> TextureRegion(tileB)
            PaintColor.PURPLE       -> TextureRegion(tileP)
        }
    }

    fun deviceToIsoCoords(coords: CartesianCoords): IsometricCoords{
        val isoFromCart = IsometricCoords(-1,-1)

        val k = tileSizeW / 4
        for(r in 0 until rows) {
            for (c in 0 until cols) {
                val tileDrawCoords = twoDtoIso(IsometricCoords(r,c))
                tileDrawCoords.x += tileSizeW/2
                tileDrawCoords.y += tileSizeH/2

                val tileMiddle = tileDrawCoords
                // substract the coords for the middle of the tile
                val cX = coords.x - tileMiddle.x
                val cY = coords.y - tileMiddle.y

                /*
                    WORKED 1st try 8) (when the user coords were send properly...)
                          |
                       Q2 | Q1
                    ------+------
                       Q3 | Q4
                          |
                    (x,y) is the coords of the point in the tile
                    Q1 equation         y <= -0.5x + K
                    Q2 equation         y <=  0.5x + K
                    Q3 equation         y >= -0.5x - K
                    Q4 equation         y >=  0.5x - K

                    The tile is centered in (0,0).
                    All equation must be true to get the right tile.

                    TileW = 4 * K
                    TileH = 2 * K
                 */

                val q1 = (cY <= -0.5*cX + k)
                val q2 = (cY <=  0.5*cX + k)
                val q3 = (cY >= -0.5*cX - k)
                val q4 = (cY >=  0.5*cX - k)

                if( q1 && q2 && q3 && q4) {
                    // Found it
                    isoFromCart.row = r
                    isoFromCart.col = c
                    return isoFromCart
                }
            }
        }
        return isoFromCart
    }

    /*
        ┌───┐  ~Isometric coords~
        └───┘  top tile (0,0)
         ...
        ┌───┐
        └───┘  last tile (side, side)
     */
    fun convertToRemoteCoords(coords: CartesianCoords): Triple<Float,Float, Float>{
        val topTileY = isoY + tileSizeH/2
        val bottomTileY = isoY + tileSizeH/2 + tileSizeH*rows

        val d = topTileY-bottomTileY
        val middleOfArenaX = screenW/2
        val middleOfArenaY = topTileY - d/2

        val x = coords.x - middleOfArenaX
        val y = coords.y - middleOfArenaY

        return Triple(x,y, tileSizeH)
    }

    fun convertFromRemoteCoords(coordsX: Float, coordsY: Float , tileH: Float): CartesianCoords{
        val tileW = tileH*2

        val curMaxX = tileSizeW*(rows/2)
        val curMaxY = tileSizeH*(rows/2)

        val remoteMaxX = tileH*(rows/2)
        val remoteMaxY = tileW*(rows/2)

        // CONVERSION
        val convertedCoord = CartesianCoords(0,0)
        convertedCoord.x = (coordsX * curMaxX / remoteMaxX).toInt()
        convertedCoord.y = (coordsY * curMaxY / remoteMaxY).toInt()

        //
        // SUM middle of arenaEntity
        val topTileY = isoY + tileSizeH/2
        val bottomTileY = isoY + tileSizeH/2 + tileSizeH*rows

        val d = topTileY-bottomTileY
        val middleOfArenaX = screenW/2
        val middleOfArenaY = topTileY - d/2

        convertedCoord.x += middleOfArenaX.toInt()
        convertedCoord.y += middleOfArenaY.toInt()

        return convertedCoord
    }

    override fun dispose() {
    }
}