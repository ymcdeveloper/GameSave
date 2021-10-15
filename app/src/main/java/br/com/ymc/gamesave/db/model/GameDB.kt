package br.com.ymc.gamesave.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Game")
data class GameDB(
    @PrimaryKey val id : Int,
    val name: String,
    val cover_id : String,
    val date : String
)
