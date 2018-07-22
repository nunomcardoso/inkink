package pt.nunomcards.inkink.gamelogic

import com.badlogic.gdx.Game
import pt.nunomcards.inkink.model.Level
import pt.nunomcards.inkink.multiplayer.MultiplayerHandler
import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 21/07/2018.
 */
class MultiPlayerLevelLogic(
        game: Game,
        level: Level
) : LevelLogic(game, level) {

    override fun update() {
        //TODO
    }

    override fun paintTile(): Boolean {
        super.paintTile()

        val pCoords = level.currentPlayer.coordsIso
        val color = level.currentPlayer.team
        MultiplayerHandler.paintTile(color, IsometricCoords(pCoords.row,pCoords.col))

        return false
    }
}