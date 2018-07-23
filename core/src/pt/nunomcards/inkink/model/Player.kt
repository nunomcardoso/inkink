package pt.nunomcards.inkink.model

import pt.nunomcards.inkink.utils.CartesianCoords
import pt.nunomcards.inkink.utils.IsometricCoords
import java.util.*

/**
 * Created by nuno on 13/07/2018.
 */
class Player(
        // Player identifier
        var id: String,
        // Color that the player will color the tiles
        var team: PaintColor = PaintColor.WHITE,
        // Device coords of the player
        val coordsCart: CartesianCoords = CartesianCoords(0,0),
        // Arena Coords of the player
        val coordsIso: IsometricCoords = IsometricCoords(0,0),
        // Weapons that the player owns
        val weapons: HashSet<Weapon> = HashSet(),
        // Ink Meter, ammount that the player has left [0, 100] (1 per block)
        var inkMeter: Float = 100f,
        // If the player is Local(true) or Remote(false)
        val currentPlayer: Boolean = false
) {
    private val previousPosition = IsometricCoords(this.coordsIso.row, this.coordsIso.col)

    fun hasMoved(): Boolean{
        if(previousPosition.row != this.coordsIso.row || previousPosition.col != this.coordsIso.col){
            // update prev.
            previousPosition.row = this.coordsIso.row
            previousPosition.col = this.coordsIso.col
            return true
        }
        return false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (id != other.id) return false
        if (coordsIso != other.coordsIso) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + coordsIso.hashCode()
        return result
    }

}