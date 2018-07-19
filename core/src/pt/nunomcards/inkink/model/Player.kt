package pt.nunomcards.inkink.model

import pt.nunomcards.inkink.utils.CartesianCoords
import pt.nunomcards.inkink.utils.IsometricCoords
import java.util.*

/**
 * Created by nuno on 13/07/2018.
 */
class Player(
        // Player identifier
        val id: String,
        // Color that the player will color the tiles
        val team: PaintColor = PaintColor.WHITE,
        // Device coords of the player
        val coordsCart: CartesianCoords = CartesianCoords(0,0),
        // Arena Coords of the player
        val coordsIso: IsometricCoords = IsometricCoords(0,0),
        // Weapons that the player owns
        val weapons: List<Weapon> = LinkedList(),
        // Ink Meter, ammount that the player has left [0, 100] (1 per block)
        val inkMeter: Float = 100f) {
}