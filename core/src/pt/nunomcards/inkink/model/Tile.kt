package pt.nunomcards.inkink.model

/**
 * Created by nuno on 13/07/2018.
 */
class Tile(var color: PaintColor = PaintColor.WHITE) {

    fun clear(){
        color = PaintColor.WHITE
    }

    override fun toString(): String {
        return "Tile(color=$color)"
    }
}