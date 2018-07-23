package pt.nunomcards.inkink.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import pt.nunomcards.inkink.assetloader.PlayerAssetLoader
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.multiplayer.MultiplayerHandler
import pt.nunomcards.inkink.utils.CartesianCoords
import pt.nunomcards.inkink.utils.GdxUtils
import pt.nunomcards.inkink.utils.GdxUtils.Companion.PPM
import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 13/07/2018.
 */
class PlayerEntity(
        val player: Player,
        private val arena: ArenaEntity,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera
) : BaseEntity(batch, world, camera) {

    private val playerTexture = PlayerAssetLoader.player
    lateinit var body: Body

    private val txtW: Float
    private val txtH: Float

    init {
        txtW = arena.tileSizeW / 2
        txtH = txtW * (playerTexture.regionHeight/ playerTexture.regionWidth)
        createPlayerBody(player.coordsIso)
    }

    private var elapsed = 0f
    override fun render() {
        updatePlayerLocation()

        elapsed+= Gdx.graphics.deltaTime
        batch.begin()

        val coords = GdxUtils.coordsBySize(arena.tileSizeW / 2, playerTexture.texture)
        batch.draw(
                PlayerAssetLoader.getKeyFrameTexture(player.team, elapsed),
                body.position.x * GdxUtils.PPM - coords.first/2,
                body.position.y * GdxUtils.PPM - coords.first/2,
                txtW,
                txtH
        )

        batch.end()

        // only current player should do this
        // in multiplayer the entities should not paint by their coords,
        // the server must tell witch tiles to color
        if(player.hasMoved() && player.currentPlayer)
            arena.colorTile(player.coordsIso.row, player.coordsIso.col, player.team)
    }

    val shapeRadius = arena.tileSizeW / 4

    // teleports the player
    fun placePlayer(isoCoords: IsometricCoords){
        // Place the place in a specific tile
        val coords = arena.twoDtoIso(isoCoords)
        // needs to be in the middle now
        coords.x += txtW
        coords.y += txtH/2

        // update positions
        player.coordsCart.x = coords.x.toInt()
        player.coordsCart.y = coords.y.toInt()
        player.coordsIso.col = isoCoords.col
        player.coordsIso.row = isoCoords.row

        body.setTransform(Vector2(coords.x,coords.y), 0f)
    }

    fun placePlayer(cartesianCoords: CartesianCoords){
        // update positions
        player.coordsCart.x = cartesianCoords.x
        player.coordsCart.y = cartesianCoords.y

        body.setTransform(Vector2(cartesianCoords.x.toFloat(), cartesianCoords.y.toFloat()), 0f)
    }

    private fun createPlayerBody(isoCoords: IsometricCoords) {
        // Place the place in a specific tile
        val coords = arena.twoDtoIso(isoCoords)
        // needs to be in the middle now
        coords.x += txtW
        coords.y += txtH/2

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

        body = boddy
    }

    private fun updatePlayerLocation(): Boolean{
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

        return true
    }

    override fun dispose(){

    }

    override fun equals(other: Any?): Boolean {
        return player.equals(other)
    }
}