package br.com.ymc.gamesave.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.ymc.gamesave.db.model.GameDB

@Dao
interface GameDAO
{
    @Query("SELECT * FROM GAME")
    fun getMyGames() : LiveData<List<GameDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(gameDB: GameDB)

    @Delete
    fun deleteGame(gameDB: GameDB)
}