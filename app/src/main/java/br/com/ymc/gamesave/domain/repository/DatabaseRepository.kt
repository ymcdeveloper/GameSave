package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.db.model.GameDB
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository
{
    suspend fun getSavedGames() : List<Game>

    suspend fun getGame(id : Int) : Game

    suspend fun insertGame(game : GameDB)

    suspend fun removeGameFromLibrary(game : GameDB)

    suspend fun checkGameExists(id : Int) : Boolean

//    suspend fun filterGames(games : List<Game>, filterQuery : String) : List<Game>

    suspend fun getCount() : Int
}
