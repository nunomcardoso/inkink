package pt.nunomcards.inkink.gamelogic

import com.badlogic.gdx.Game
import pt.nunomcards.inkink.model.Level
import pt.nunomcards.inkink.model.Weapon

/**
 * Created by nuno on 20/07/2018.
 */
abstract class LevelLogic(
        val game: Game,
        val level: Level
) {

    abstract fun update()

    protected open fun paintTile(): Boolean{
        // verify coords
        val pCoords = level.currentPlayer.coordsIso
        val arena = level.arena
        val color = level.currentPlayer.team

        if(pCoords.row > arena.rows || pCoords.row<0 || pCoords.col>arena.columns || pCoords.col<0)
            return false

        val previousColor = arena.map[pCoords.row][pCoords.col].color
        arena.map[pCoords.row][pCoords.col].color = color

        return previousColor != color
    }

    fun getBombAmmo(): Int{
        return getAmmo(Weapon.WeaponType.BOMB)
    }

    fun getCannonAmmo(): Int{
        return getAmmo(Weapon.WeaponType.CANNON)
    }

    private fun getAmmo(w: Weapon.WeaponType): Int{
        return try {
            level.currentPlayer.weapons.first { e -> e.weaponType == w }.ammoRemaining()
        } catch (e: NoSuchElementException){
            0
        }
    }
}