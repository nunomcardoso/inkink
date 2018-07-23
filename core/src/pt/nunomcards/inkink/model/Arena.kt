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
    var map: Array<Array<Tile>> = Array(rows, {Array(columns, {Tile()})})

    // coins, crates and warp points
    val tileObjs: LinkedList<TileObject> = LinkedList()

    fun paintAllMap(color: PaintColor){
        for(r in 0 until rows){
            for(c in 0 until columns)
                map[r][c].color = color
        }
    }

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
        val c = playerPosition.col
        val r = playerPosition.row

        val coords = arrayOf(
                IsometricCoords(r,c+1),
                IsometricCoords(r,c-1),
                IsometricCoords(r+1,c+1),
                IsometricCoords(r-1,c-1),
                IsometricCoords(r+1,c-1),
                IsometricCoords(r-1,c+1),
                IsometricCoords(r+1,c),
                IsometricCoords(r-1,c)
        )

        for(p in coords){
            try{
                map[p.row][p.col].color  = color
            } catch (e: Exception){/* continue */}
        }
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
        val c = playerPosition.col
        val r = playerPosition.row

        val coords = arrayOf(
                IsometricCoords(r-1,c),
                IsometricCoords(r-2,c),
                IsometricCoords(r+1,c),
                IsometricCoords(r+2,c),
                IsometricCoords(r,c-1),
                IsometricCoords(r,c-2),
                IsometricCoords(r,c+1),
                IsometricCoords(r,c+2)
        )

        for(p in coords){
            try{
                map[p.row][p.col].color  = color
            } catch (e: Exception){/* continue */}
        }
    }

    /**
     * From remote
     */
    fun createFromRemote(remoteMap: Array<Array<Tile>>){
        map = remoteMap
    }
}