package pt.nunomcards.inkink.entities

/**
 * Created by nuno on 13/07/2018.
 */
open class LevelEntity : BaseEntity{

    lateinit var currentPlayer: PlayerEntity
    lateinit var players: List<PlayerEntity>

    lateinit var arena: ArenaEntity
    lateinit var stats: StatsEntity

    override fun render() {

    }
}