package pt.nunomcards.inkink.gameinput

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import pt.nunomcards.inkink.entities.PlayerEntity
import pt.nunomcards.inkink.gamelogic.PlayerMovement

/**
 * Created by nuno on 18/07/2018.
 */
object InputHandler{

    fun movePlayer(player: PlayerEntity, mv: Movement = Movement.AUTO){
        // When there is no movement the player must be still
        PlayerMovement.stopMovement(player)

        if(mv == Movement.AUTO)
            moveKeyboard(player)
        else
            moveHUD(player, mv)
    }

    private fun moveHUD(player: PlayerEntity, mv: Movement){
        when(mv){
            Movement.UP -> PlayerMovement.moveUp(player)
            Movement.DOWN -> PlayerMovement.moveDown(player)
            Movement.LEFT -> PlayerMovement.moveLeft(player)
            Movement.RIGHT -> PlayerMovement.moveRight(player)
            else -> {
                // Do nothing
            }
        }
    }

    private fun moveKeyboard(player: PlayerEntity){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            PlayerMovement.moveLeft(player)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            PlayerMovement.moveRight(player)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            PlayerMovement.moveUp(player)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            PlayerMovement.moveDown(player)
        }
    }
}