package pt.nunomcards.inkink.model

import pt.nunomcards.inkink.utils.IsometricCoords
import java.util.*

/**
 * Created by nuno on 13/07/2018.
 */

class Arena (
        val rows: Int,
        val columns: Int
) {

    // creates the bi-dimensional array
    val map: Array<Array<Tile>> = Array(rows, {Array(columns, {Tile()})})

    // coins, crates and warp points
    val tileObjs: LinkedList<TileObject> = LinkedList()

    /**
     *      |   BOMB   |
     * "X" is the (row, col) position
     * "*" is the area of effect
     * "." is non painted tiles
     *          . . . . .
     *          . * * * .
     *          . * X * .
     *          . * * * .
     *          . . . . .
     */
    fun placeBombInk(playerPosition: IsometricCoords, color: PaintColor){

    }

    /**
     *      |   CANNON   |
     * "X" is the (row, col) position
     * "*" is the area of effect
     * "." is non painted tiles
     *          . . * . .
     *          . . * . .
     *          * * X * *
     *          . . * . .
     *          . . * . .
     */
    fun shootCannonInk(playerPosition: IsometricCoords, color: PaintColor){

    }
}