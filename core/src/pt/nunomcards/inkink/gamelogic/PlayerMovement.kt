package pt.nunomcards.inkink.gamelogic

import com.badlogic.gdx.math.Vector2
import pt.nunomcards.inkink.entities.PlayerEntity

/**
 * Created by nuno on 13/07/2018.
 */
class PlayerMovement(val player: PlayerEntity) {

    // Movement Speed
    private val speed = 5f

    // Stops body momentum
    fun stopVelocity(){
        player.body.linearVelocity = Vector2(0f,0f)
    }

    fun moveUp(){
        stopVelocity()
        player.body.applyLinearImpulse(
                Vector2(0f, speed), player.body.worldCenter, true
        )
    }

    fun moveDown(){
        stopVelocity()
        player.body.applyLinearImpulse(
                Vector2(0f, -speed), player.body.worldCenter, true
        )
    }

    fun moveLeft(){
        stopVelocity()
        player.body.applyLinearImpulse(
                Vector2(-speed, 0f), player.body.worldCenter, true
        )
    }

    fun moveRight(){
        stopVelocity()
        player.body.applyLinearImpulse(
                Vector2(speed, 0f), player.body.worldCenter, true
        )
    }
}