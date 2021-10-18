package br.com.ymc.gamesave.model

import br.com.ymc.gamesave.db.model.GameDB
import br.com.ymc.gamesave.db.model.toGame
import java.text.SimpleDateFormat
import java.util.*

data class Game(
    val cover: Cover?,
    val id: Int,
    val name: String,
    val summary: String = "",
    val total_rating: Float?,
    val date : String?
)

fun Game.toGameDB() : GameDB
{
    val sdf = SimpleDateFormat("dd/M/yyyy", Locale("pt", "BR"))
    val currentDate = sdf.format(Date())

    return GameDB(
        id = this.id,
        name = this.name,
        cover_id = this.cover?.image_id,
        date = currentDate,
        summary = this.summary
    )
}