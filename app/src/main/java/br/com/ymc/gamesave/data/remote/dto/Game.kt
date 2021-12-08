package br.com.ymc.gamesave.data.remote.dto

import br.com.ymc.gamesave.data.local.entity.GameDB
import br.com.ymc.gamesave.domain.model.Cover
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class Game(
    val cover: Cover?,
    val id: Int,
    val name: String,
    var summary: String?,
    @SerializedName("total_rating") val totalRating: Float?,
    @SerializedName("first_release_date") val releaseDate: Long?,
    val date : String?
)

fun Game.toGameDB() : GameDB
{
    val sdf = SimpleDateFormat("dd/M/yyyy", Locale("pt", "BR"))
    val currentDate = sdf.format(Date())

    if(this.summary.isNullOrEmpty())
    {
        this.summary = ""
    }

    return GameDB(
        id = this.id,
        name = this.name,
        cover_id = this.cover?.image_id,
        date = currentDate,
        summary = this.summary!!,
        releaseDate = this.releaseDate,
        rating = this.totalRating
    )
}