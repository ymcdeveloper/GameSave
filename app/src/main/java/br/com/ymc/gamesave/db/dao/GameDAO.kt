package br.com.ymc.gamesave.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.ymc.gamesave.db.model.GameDB
import br.com.ymc.gamesave.model.Game

@Dao
interface GameDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(gameDB: GameDB)

    @Query("SELECT * FROM GAME")
    suspend fun selectSavedGames() : List<GameDB>

    @Query("SELECT * FROM GAME WHERE id = :id")
    suspend fun selectGameById(id : Int) : GameDB

    @Query("SELECT EXISTS(SELECT * FROM GAME WHERE id = :id)")
    suspend fun gameExists(id : Int) : Boolean

    @Delete
    suspend fun deleteGame(gameDB: GameDB)
}