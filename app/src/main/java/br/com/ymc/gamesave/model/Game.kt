package br.com.ymc.gamesave.model

data class Game(
    val cover: Cover?,
    val id: Int,
    val name: String,
    val summary: String,
    val total_rating: Float
)