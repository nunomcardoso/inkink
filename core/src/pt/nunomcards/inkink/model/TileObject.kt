package pt.nunomcards.inkink.model

import pt.nunomcards.inkink.utils.IsometricCoords

/**
 * Created by nuno on 13/07/2018.
 */
class TileObject(val obj: ObjectType, val isometricCoords: IsometricCoords) {

}

enum class ObjectType{
    CHEST_BOMB,
    CHEST_CANNON,
    FINAL_PLATFORM,
    COIN,
}
