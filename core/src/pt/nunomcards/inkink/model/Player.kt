package pt.nunomcards.inkink.model

import pt.nunomcards.inkink.utils.CartesianCoords
import java.util.*

/**
 * Created by nuno on 13/07/2018.
 */
class Player(
        // Player identifier
        val id: String,
        // Color that the player will color the tiles
        val team: PaintColor,
        // Actual coords of the player
        val coords: CartesianCoords,
        // Weapons that the player owns
        val weapons: List<Weapon> = LinkedList(),
        // Ink Meter, ammount that the player has left [0, 100] (1 per block)
        val inkMeter: Float = 100f) {
}