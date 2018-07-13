package pt.nunomcards.inkink.model

/**
 * Created by nuno on 13/07/2018.
 */
class Weapon(
        private var ammo: Int
) {

    fun shoot(){
        if(ammo > 0)
            ammo--
    }

    fun ammoRemaining(): Int{
        return ammo
    }
}