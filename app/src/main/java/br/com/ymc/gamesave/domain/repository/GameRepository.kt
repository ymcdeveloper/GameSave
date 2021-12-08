package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.remote.dto.Game
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GameRepository
{
    suspend fun getGames() : Flow<Resource<List<Game>>>

    suspend fun getGameById(id: Int) : Flow<Resource<Game>>

    suspend fun searchGame(searchQuery: String) : Flow<Resource<List<Game>>>
}