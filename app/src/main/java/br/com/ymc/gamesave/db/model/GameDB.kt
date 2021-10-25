package br.com.ymc.gamesave.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.ymc.gamesave.model.Cover
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.model.toGameDB

@Entity(tableName = "Game")
data class GameDB(
    @PrimaryKey val id : Int,
    val name: String,
    val cover_id : String?,
    val date : String,
    val summary : String = "",
    val releaseDate : Long?,
    val rating : Float?
)

fun GameDB.toGame() : Game
{
    return Game(
        id = this.id,
        name = this.name,
        date = this.date,
        summary = this.summary,
        cover = this.cover_id?.let { Cover(0, it) },
        totalRating = this.rating,
        releaseDate = this.releaseDate
    )
}
