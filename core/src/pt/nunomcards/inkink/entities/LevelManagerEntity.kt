package pt.nunomcards.inkink.entities

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import pt.nunomcards.inkink.gamelogic.LevelLogic
import pt.nunomcards.inkink.gamelogic.MultiPlayerLevelLogic
import pt.nunomcards.inkink.gamelogic.SinglePlayerLevelLogic
import pt.nunomcards.inkink.model.GameMode
import pt.nunomcards.inkink.model.Level
import pt.nunomcards.inkink.model.PaintColor
import pt.nunomcards.inkink.multiplayer.MultiplayerHandler

/**
 * Created by nuno on 13/07/2018.
 */
class LevelManagerEntity(
        game: Game,
        batch: SpriteBatch,
        world: World,
        camera: OrthographicCamera,
        level: Level
) : BaseEntity(batch, world, camera) {

    private val levelEntity: LevelEntity
    private val logic: LevelLogic
    private val hud: HUDEntity

    init {
        when(level.gameMode){
            GameMode.SINGLEPLAYER -> {
                level.currentPlayer.team = PaintColor.WHITE
                // Game Logic
                logic = SinglePlayerLevelLogic(game, level)
                // Level Entity
                levelEntity = SinglePlayerLevelEntity(level, batch, world, camera)
                // HUD
                hud = HUDSinglePlayerEntity(levelEntity, logic, batch, world, camera)
            }
            GameMode.MULTIPLAYER -> {
                // Game Logic
                logic = MultiPlayerLevelLogic(game, level)
                // Level Entity
                levelEntity = MultiPlayerLevelEntity(level, batch, world, camera)
                // HUD
                hud = HUDMultiPlayerEntity(levelEntity, logic, batch, world, camera)

                MultiplayerHandler.init(levelEntity)
            }
        }
    }

    override fun render() {
        // Update Logic
        logic.update()

        // HUD
        hud.render()

        // Render
        levelEntity.render()
    }

    override fun dispose() {
        hud.dispose()
    }

}