package pt.nunomcards.inkink.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import pt.nunomcards.inkink.model.Player
import pt.nunomcards.inkink.utils.GdxUtils
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

    init {
        body = placePlayer(IsometricCoords(0,0))
    }

    override fun render() {
        batch.begin()

        val coords = GdxUtils.coordsBySize(arena.tileSizeW / 2, playerTexture.texture)
        val txtW = arena.tileSizeW / 2
        val txtH = txtW * (playerTexture.regionHeight/ playerTexture.regionWidth)
        batch.draw(
                playerTexture,
                body.position.x * GdxUtils.PPM - coords.first/2,
                body.position.y * GdxUtils.PPM - coords.first/2,
                txtW,
                txtH
        )

        batch.end()
    }

    private fun getAnimation(){
        player.team
    }

    fun placePlayer(isoCoords: IsometricCoords): Body {
        val shapeRadius = arena.tileSizeW / 4

        // Place the place in a specific tile
        isoCoords.row-=1 // for some reason this needs to have -1 rows
        val coords = arena.twoDtoIso(isoCoords)

        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(coords.x / GdxUtils.PPM, coords.y / GdxUtils.PPM)

        val boddy = world.createBody(bodyDef)

        val shape = CircleShape()
        shape.radius = shapeRadius/ GdxUtils.PPM

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        boddy.createFixture(fixtureDef)

        boddy.userData = "player"

        return boddy
    }

    override fun dispose(){

    }
}