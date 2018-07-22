package pt.nunomcards.inkink.model

import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 13/07/2018.
 */
class Level(
        val playerInitCoords: IsometricCoords,
        val arena: Arena,
        val currentPlayer: Player,
        val playerList: List<Player>? = null,
        val gameMode: GameMode = GameMode.SINGLEPLAYER,
        val timeToComplete: Int = 30,
        var levelState: LevelState = LevelState.STARTED
)
{
    init{
        // SET PLAYER COORDS
        currentPlayer.coordsIso.row = playerInitCoords.row
        currentPlayer.coordsIso.col = playerInitCoords.col
    }
}