package pt.nunomcards.inkink.gamelogic

import com.badlogic.gdx.Game
import pt.nunomcards.inkink.assetloader.AudioAssets
import pt.nunomcards.inkink.model.Level
import pt.nunomcards.inkink.model.LevelState
import pt.nunomcards.inkink.multiplayer.MultiplayerHandler
import pt.nunomcards.inkink.screens.MultiPlayerScoreScreen
import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 21/07/2018.
 */
class MultiPlayerLevelLogic(
        game: Game,
        level: Level
) : LevelLogic(game, level) {

    val percentages = IntArray(6, {0})
    var initTime: Long = System.currentTimeMillis()+1000

    override fun update() {
        // Update percentages
        updatePercentages()

        if(level.levelState == LevelState.ENDED) {
            MultiplayerHandler.end()
            // AUDIO
            AudioAssets.mpLevelMusic.stop()
            AudioAssets.endLevel.play()
            game.screen = MultiPlayerScoreScreen(
                    game, percentages
            )
            MultiplayerHandler.endGame()
        }

        // Check if timer ran out
        if( System.currentTimeMillis() > initTime + level.timeToComplete * 1000 ){
            level.levelState = LevelState.ENDED
            return
        }

        // Player paints
        paintTile()
    }

    private fun updatePercentages(){
        val rows = level.arena.rows
        val cols = level.arena.columns
        val percentagesTemp = IntArray(6, {0})

        // update scores
        for(r in 0 until rows){
            (0 until cols)
                    .forEach {
                        val color = level.arena.map[r][it].color.ordinal
                        if(color > 0)
                            percentagesTemp[color-1]++
                    }
        }
        for(i in 0 until percentages.size){
            percentages[i] = ((percentagesTemp[i].toFloat()/(rows*rows)) * 100).toInt()
        }
    }

    override fun paintTile(): Boolean {
        super.paintTile()

        val pCoords = level.currentPlayer.coordsIso
        val color = level.currentPlayer.team

        //if( level.currentPlayer.hasMoved() ) {
            MultiplayerHandler.moveCurrentPlayer(level.currentPlayer.id, level.currentPlayer.coordsIso)
            MultiplayerHandler.paintTile(color, IsometricCoords(pCoords.row, pCoords.col))
        //}

        return true
    }

    fun getTimer(): Int {
        return level.timeToComplete-(System.currentTimeMillis()-initTime).toInt()/1000
    }
}