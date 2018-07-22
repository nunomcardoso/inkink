package pt.nunomcards.inkink.model

/**
 * Created by nuno on 13/07/2018.
 */
class Weapon(
        val weaponType: WeaponType,
        private var ammo: Int
) {
    enum class WeaponType{
        BOMB, CANNON
    }

    fun shoot(){
        if(ammo > 0)
            ammo--
    }

    fun ammoRemaining(): Int{
        return ammo
    }
}