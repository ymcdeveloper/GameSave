package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.local.entity.GameDB
import br.com.ymc.gamesave.data.remote.dto.Game
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository
{
    suspend fun getSavedGames() : Flow<Resource<List<Game>>>

    suspend fun getGame(id : Int) : Flow<Resource<Game>>

    suspend fun insertGame(game : GameDB)

    suspend fun removeGameFromLibrary(game : GameDB)

    suspend fun checkGameExists(id : Int) : Boolean

//    suspend fun filterGames(games : List<Game>, filterQuery : String) : List<Game>

    suspend fun getCount() : Int
}