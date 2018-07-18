package pt.nunomcards.inkink.gamelogic

import com.badlogic.gdx.math.Vector2
import pt.nunomcards.inkink.entities.PlayerEntity
import java.lang.Math.abs

/**
 * Created by nuno on 13/07/2018.
 */
object PlayerMovement {

    // Movement Speed
    private val speed = 10f
    private val stopVector = Vector2(0f,0f)

    // Stops body momentum
    fun stopMovement(player: PlayerEntity){
        player.body.linearVelocity = stopVector
    }

    fun moveUp(player: PlayerEntity){
        player.body.applyLinearImpulse(
                Vector2(0f, speed), player.body.worldCenter, true
        )
    }

    fun moveDown(player: PlayerEntity){
        player.body.applyLinearImpulse(
                Vector2(0f, -speed), player.body.worldCenter, true
        )
    }

    fun moveLeft(player: PlayerEntity){
        player.body.applyLinearImpulse(
                Vector2(-speed, 0f), player.body.worldCenter, true
        )
    }

    fun moveRight(player: PlayerEntity){
        player.body.applyLinearImpulse(
                Vector2(speed, 0f), player.body.worldCenter, true
        )
    }
}