package pt.nunomcards.inkink.utils

/**
 * Created by nuno on 13/07/2018.
 */
class IsometricCoords(var row: Int, var col: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IsometricCoords

        if (row != other.row) return false
        if (col != other.col) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }
}