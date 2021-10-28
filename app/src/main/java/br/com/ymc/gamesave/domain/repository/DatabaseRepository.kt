package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.db.model.GameDB
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository
{
    suspend fun getSavedGames() : Flow<Resource<List<Game>>>

    suspend fun getGame(id : Int) : Flow<Resource<Game>>

    suspend fun saveGameToLibrary(game : GameDB)

    suspend fun removeGameFromLibrary(game : GameDB)

    suspend fun checkGameExists(id : Int) : Boolean

    suspend fun filterGames(games : List<Game>, filterQuery : String) : Flow<Resource<List<Game>>>

    suspend fun getCount() : Int
}