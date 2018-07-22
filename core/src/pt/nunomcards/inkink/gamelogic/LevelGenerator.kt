package pt.nunomcards.inkink.gamelogic

import pt.nunomcards.inkink.model.*
import pt.nunomcards.inkink.utils.IsometricCoords
import java.util.*

/**
 * Created by nuno on 20/07/2018.
 */
/*
 * TODO: Create a file decoder to read LevelFiles, so they are not hard-coded
 */
object LevelGenerator {

    /**
     *
     *  row --->
     *  col \/
     *      B - Spawn point
     *      F - Final point (To complete the level)
     *      * - Paint
     *      C - Coin
     *      B - Chest (BOMB)
     *      K - Chest (CANNON)
     */
    fun getLevel(color: PaintColor): Level{
        return when(color){
            PaintColor.RED -> levelOne(color)
            //PaintColor.ORANGE -> levelTwo(color)
            //PaintColor.YELLOW -> levelThree(color)
            //PaintColor.GREEN -> levelOne(color)
            //PaintColor.BLUE -> levelTwo(color)
            //PaintColor.PURPLE -> levelThree(color)

            else -> levelOne(color)
        }
    }

    fun getMultiPlayerLevel(color: PaintColor): Level{
        val initCoords = IsometricCoords(0,0)
        val arena = Arena(10,10)

        val player = Player("", color)
        return Level(initCoords,arena,player)
    }

    // ┌ ┘ └ ┴ ┬ ┐ ├ ─ ┤ │
    /**
     *  LEVEL RED / GREEN
     *  (0,0)
     *  ┌───┬───┬───┬───┐
     *  │ B │ C │ * | * |
     *  ├───┼───┼───┼───┤
     *  │   │   │ * | * |
     *  ├───┼───┼───┼───┤
     *  │ * │   │   |   |
     *  ├───┼───┼───┼───┤
     *  │ C │   │ F | C |
     *  └───┴───┴───┴───┘ (4,4)
     */
    private fun levelOne(color: PaintColor): Level {
        val initCoords = IsometricCoords(0,0)
        val arena = Arena(7,7)
        val tileObjs: LinkedList<TileObject> = arena.tileObjs

        // COINS
        tileObjs.add(TileObject(ObjectType.COIN, IsometricCoords(0,1)))
        tileObjs.add(TileObject(ObjectType.COIN, IsometricCoords(3,0)))
        tileObjs.add(TileObject(ObjectType.COIN, IsometricCoords(3,3)))

        // FINAL PLATFORM
        tileObjs.add(TileObject(ObjectType.FINAL_PLATFORM, IsometricCoords(4,3)))

        // COLORED TILES
        arena.map[0][2].color = color
        arena.map[0][3].color = color
        arena.map[1][2].color = color
        arena.map[1][3].color = color
        arena.map[2][0].color = color

        val player = Player("-", color)
        return Level(initCoords,arena,player)
    }

    /**
     *  LEVEL ORANGE / BLUE
     *  (0,0)
     *  ┌───┬───┬───┬───┬───┬───┬───┐
     *  │ C │ * │   |   | * |   | C |
     *  ├───┼───┼───┼───┼───┼───┼───┤
     *  │ * │ * │   |   |   | * |   |
     *  ├───┼───┼───┼───┼───┼───┼───┤
     *  │   │   │ I |   | * | C | C |
     *  ├───┼───┼───┼───┼───┼───┼───┤
     *  │   │ * │   |   |   | * |   |
     *  ├───┼───┼───┼───┼───┼───┼───┤
     *  │ C │   │ * |   |   |   |   |
     *  ├───┼───┼───┼───┼───┼───┼───┤
     *  │ C │ * │   |   | C | F |   |
     *  ├───┼───┼───┼───┼───┼───┼───┤
     *  │   │ * │   |   | C | * | * |
     *  └───┴───┴───┴───┴───┴───┴───┘ (7,7)
     */
    private fun levelTwo(color: PaintColor): Level {
        val initCoords = IsometricCoords(0,0)
        val arena = Arena(7,7)
        val tileObjs: LinkedList<TileObject> = arena.tileObjs

        val player = Player("-", color)
        return Level(initCoords,arena,player)
    }

    /**
     *  LEVEL YELLOW / PURPLE
     *  (0,0)
     *  ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
     *  │ I │ B │ * | * | * | * | * | * | K | C |
     *  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
     *  │ * │ * │ * | * | * | * | * | * | * | * |
     *  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
     *  │ * │ * │ * | * | * | * | * | * | * | * |
     *  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
     *  │ * │ * │ * | * | * | * | * | * | * | * |
     *  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
     *  │ * │ * │ * | * | * | * | * | * | * | * |
     *  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
     *  │ * │ * │ * | * | * | * | * | * | * | * |
     *  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
     *  │ * │ * │ * | * | * | * | * | * | * | * |
     *  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
     *  │ * │ * │ * | * | * | * | * | * | * | * |
     *  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
     *  │ * │ * │ * | * | * | * | * | * | * | * |
     *  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
     *  │ C │ * │ * | * | * | * | * | * | * | F |
     *  └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘ (10,10)
     */
    private fun levelThree(color: PaintColor): Level {
        val initCoords = IsometricCoords(0,0)
        val arena = Arena(10,10)
        val tileObjs: LinkedList<TileObject> = arena.tileObjs

        val player = Player("-", color)
        return Level(initCoords,arena,player)
    }


}