package pt.nunomcards.inkink.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.utils.CartesianCoords
import pt.nunomcards.inkink.utils.GdxUtils
import pt.nunomcards.inkink.utils.GdxUtils.Companion.PPM
import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 13/07/2018.
 */
class PlayerEntity(
        private val player: Player,
        private val arena: ArenaEntity,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : BaseEntity(batch, world, camera) {

    private val playerTexture = TextureRegion(Texture("player.png"))
    val body: Body

    private val txtW: Float
    private val txtH: Float

    init {
        txtW = arena.tileSizeW / 2
        txtH = txtW * (playerTexture.regionHeight/ playerTexture.regionWidth)
        body = placePlayer(player.coordsIso)
    }

    override fun render() {
        updatePlayerLocation()

        batch.begin()

        val coords = GdxUtils.coordsBySize(arena.tileSizeW / 2, playerTexture.texture)
        batch.draw(
                playerTexture,
                body.position.x * GdxUtils.PPM - coords.first/2,
                body.position.y * GdxUtils.PPM - coords.first/2,
                txtW,
                txtH
        )

        batch.end()

        // only current player should do this
        // in multiplayer the entities should not paint by their coords,
        // the server must tell witch tiles to color
        arena.colorTile(player.coordsIso.row, player.coordsIso.col, PaintColor.RED)
    }

    private fun getAnimation(){
        player.team
    }


    fun placePlayer(isoCoords: IsometricCoords): Body {
        // Place the place in a specific tile
        val coords = arena.twoDtoIso(isoCoords)
        // needs to be in the middle now
        coords.x += txtW
        coords.y += txtH/2

        val shapeRadius = arena.tileSizeW / 4

        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(coords.x / GdxUtils.PPM, coords.y / GdxUtils.PPM)

        val boddy = world.createBody(bodyDef)

        val shape = CircleShape()
        shape.radius = shapeRadius/ GdxUtils.PPM

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        boddy.createFixture(fixtureDef)

        boddy.userData = player.id

        return boddy
    }


    fun updatePlayerLocation(){
        val shapeRadius = arena.tileSizeW / 4

        val bodyX = body.position.x * PPM
        val bodyY = body.position.y * PPM

        // update Player Coords
        player.coordsCart.x = bodyX.toInt()
        player.coordsCart.y = bodyY.toInt()

        val bodyCoords = CartesianCoords(bodyX.toInt(), (bodyY- shapeRadius*.8).toInt())

        val iso = arena.deviceToIsoCoords(bodyCoords)
        // update Player Coords
        player.coordsIso.row = iso.row
        player.coordsIso.col = iso.col
    }

    override fun dispose(){

    }
}