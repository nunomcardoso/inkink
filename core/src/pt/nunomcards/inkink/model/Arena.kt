package pt.nunomcards.inkink.model

import java.util.*

/**
 * Created by nuno on 13/07/2018.
 */

class Arena (rows: Int, columns: Int) {

    // creates the bi-dimensional array
    val map: Array<Array<Tile>> = Array(rows, {Array(columns, {Tile()})})

    // coins, crates and warp points with actual position
    val tileObjs: List<Pair<TileObject,Object>> = LinkedList()

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
    fun placeBombInk(row: Int, col: Int){

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
    fun shootCannonInk(row: Int, col: Int){

    }
}