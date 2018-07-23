package pt.nunomcards.inkink.gamelogic

import com.badlogic.gdx.Game
import pt.nunomcards.inkink.assetloader.AudioAssets
import pt.nunomcards.inkink.model.*
import pt.nunomcards.inkink.screens.SinglePlayerScoreScreen

/**
 * Created by nuno on 21/07/2018.
 */
class SinglePlayerLevelLogic(
        game: Game,
        level: Level
): LevelLogic(game, level) {

    private val player = level.currentPlayer

    var coins = 0
    var tilesPainted = 0

    val maxCoins : Int
    val maxTilesPainted: Int

    val initTime: Long

    init {
        var tmpCoins = 0
        var tmpTiles = 0
        //
        // COUNT every tile painted
        //
        for(r in 0 until level.arena.rows){
            (0 until level.arena.columns)
                    .filter { level.arena.map[r][it].color != PaintColor.WHITE }
                    .forEach { tmpTiles++ }
        }

        //
        // COUNT coins
        //
        level.arena.tileObjs.forEach { tile ->
            if(tile.obj == ObjectType.COIN){
                tmpCoins++
            }
        }

        // level requirements
        maxCoins = tmpCoins
        maxTilesPainted = tmpTiles

        // START initial time
        initTime = System.currentTimeMillis()
    }

    override fun update() {
        if(level.levelState == LevelState.ENDED) {
            // AUDIO
            AudioAssets.spLevelMusic.stop()
            AudioAssets.endLevel.play()
            game.screen = SinglePlayerScoreScreen(
                    game,
                    Pair(coins,maxCoins),
                    ((tilesPainted.toFloat()/maxTilesPainted.toFloat())*100).toInt(),
                    (level.timeToComplete-(System.currentTimeMillis()-initTime)/1000).toInt()
            )
        }

        // Check if timer ran out
        if( System.currentTimeMillis() > initTime + level.timeToComplete * 1000 ){
            level.levelState = LevelState.ENDED
            return
        }

        paintTile()
        countTilesLeft()

        // According to tile position is possible to do something different
        level.arena.tileObjs.forEach { tile ->
            if(tile.isometricCoords == player.coordsIso){
                // remove the tile (so player cannot get it again
                doAction(tile.obj)
                level.arena.tileObjs.remove(tile)
                return
            }
        }
    }

    private fun countTilesLeft(){
        var tiles = 0
        for(r in 0 until level.arena.rows){
            (0 until level.arena.columns)
                    .filter { level.arena.map[r][it].color != PaintColor.WHITE }
                    .forEach { tiles++ }
        }
        tilesPainted = maxTilesPainted - tiles
    }

    private fun doAction(obj: ObjectType) {
        when(obj){
            ObjectType.CHEST_BOMB -> {
                AudioAssets.chestOpenSound.play()
                player.weapons.add(Weapon(Weapon.WeaponType.BOMB, 3))
            }
            ObjectType.CHEST_CANNON -> {
                AudioAssets.chestOpenSound.play()
                player.weapons.add(Weapon(Weapon.WeaponType.CANNON, 3))
            }
            ObjectType.FINAL_PLATFORM -> {
                level.levelState = LevelState.ENDED
            }
            ObjectType.COIN -> {
                AudioAssets.coinSound.play()
                coins++
            }
        }
    }

    fun getTimer(): String{
        return (level.timeToComplete-(System.currentTimeMillis() - initTime)/1000).toString()
    }
}