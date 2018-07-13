package pt.nunomcards.inkink.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.physics.box2d.*

/**
 * Created by nuno on 04/07/2018.
 */
class LevelScreen : Screen {
    private val tile = Texture("level/tile-flat.png")

    private val batch = SpriteBatch()
    private val game: Game
    private var stage: Stage
    private val debugRenderer: Box2DDebugRenderer

    var torque = 0.0f
    var drawSprite = true
    val PPM = 100f

    val BOX_STEP = 1 / 60f
    val BOX_VELOCITY_ITERATIONS = 6
    val BOX_POSITION_ITERATIONS = 2

    private var player: Body? = null

    private val world: World

    private val camera: OrthographicCamera

    constructor(game: Game, lvl: Int) {
        this.game = game
        stage = Stage(ScreenViewport(), batch)
        Gdx.input.inputProcessor = stage

        // Game implements ApplicationListener that delegates to a screen
        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat() / PPM, Gdx.graphics.height.toFloat() / PPM)
        // Setting the camera's initial position to the bottom left of the map. The camera's position is in the center of the camera
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f)
        // Update our camera
        camera.update()
        // Update the batch with our Camera's view and projection matrices
        batch.projectionMatrix = camera.combined

        world = World(Vector2(0f, 0f), true)
        debugRenderer = Box2DDebugRenderer()
        player = createPlayer(0,0)
    }

    override fun hide() {}

    override fun show() {}

    // DIMENSIONS
    private val h = Gdx.graphics.height.toFloat()
    private val w = Gdx.graphics.width.toFloat()
    /**
     *          RENDER
     */
    override fun render(delta: Float) {
        camera.update()

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT)
        update(delta)

        batch.begin()
        renderArena()
        renderArenaLimitationsBox2D();

        val chest = Texture("chest_sprite.png")
        drawIsometric(4,4, chest)
        val player = Texture("player.png")
        drawIsometric(playerC,playerR, player)

        batch.end()

        debugRenderer.render(world, camera.combined)

        stage.act(Gdx.graphics.rawDeltaTime)
        stage.draw()

        world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS)
    }

    private fun drawIsometric(c: Int, r: Int, texture: Texture){
        val txtW = tileSizeW / 2
        val txtH = txtW * (texture.height / texture.width)
        val coords = twoDtoIso(r,c)
        coords.x += txtW/2
        coords.y += txtH/4      // so the sprite is centered
        batch.draw(texture, coords.x, coords.y, txtW, txtH)
    }

    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}
    override fun dispose() {}

    /**
     * PLAYER MOVEMENT
     */
    var playerC = 0
    var playerR = 0
    private fun update(delta: Float){
        player!!.linearVelocity = Vector2(0f,0f)
        val speed = 5f
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player!!.applyLinearImpulse(Vector2(-speed, 0f), player!!.getWorldCenter(), true)
            //player!!.applyForceToCenter(-speed,0f,true)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player!!.applyLinearImpulse(Vector2(speed, 0f), player!!.getWorldCenter(), true)
            //player!!.applyForceToCenter(speed,0f,true)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player!!.applyLinearImpulse(Vector2(0f, speed), player!!.getWorldCenter(), true)
            //player!!.applyForceToCenter(0f,speed,true)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player!!.applyLinearImpulse(Vector2(0f, -speed), player!!.getWorldCenter(), true)
            //player!!.applyForceToCenter(0f, -speed,true)
        }
    }

    /**
     *          PLAYER CREATOR
     */
    private fun createPlayer(row: Int, col: Int): Body {
        val shapeRadius = tileSizeW / 4
        val coords = twoDtoIso(row-1,col) // for some reason this needs to have -1 rows
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(coords.x / PPM, coords.y / PPM)

        val body = world.createBody(bodyDef)

        val shape = CircleShape()
        shape.radius = shapeRadius/PPM

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        body.createFixture(fixtureDef)
        body.userData = "player"

        return body
    }

    /**
     *          ARENA
     */
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

    /**
     *      ARENA LIMITS
     */
    val limitAngle = (30+180) * MathUtils.degreesToRadians
    private fun renderArenaLimitationsBox2D(){
        // DIVIDIR por PPM
        // Assim consegue-se melhores medidas e angulos

        val cvpH  = camera.viewportHeight
        val cvpW = camera.viewportWidth
        //vertical offset
        val offset = -1f
        val limlength = arenaCols*3
        val coords = arrayOf(
                // LEFT limits
                Triple(Vector2(0f,cvpH/2+offset), Vector2(0f,0f), Vector2(1f*limlength,0.5f*limlength)),
                Triple(Vector2(0f,cvpH/2+offset), Vector2(0f,0f), Vector2(1f*limlength,-0.5f*limlength)),
                // RIGHT limits
                Triple(Vector2(cvpW,cvpH/2+offset), Vector2(0f,0f), Vector2(-1f*limlength,-0.5f*limlength)),
                Triple(Vector2(cvpW,cvpH/2+offset), Vector2(0f,0f), Vector2(-1f*limlength,0.5f*limlength))
        )

        for(cd in coords) {
            var limits = BodyDef()
            limits.position.set(cd.first)
            //limits.angle
            var limitBody = world.createBody(limits)
            var limitBox = EdgeShape()
            limitBox.set(cd.second, cd.third)
            limitBody.createFixture(limitBox, 0.0f)
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