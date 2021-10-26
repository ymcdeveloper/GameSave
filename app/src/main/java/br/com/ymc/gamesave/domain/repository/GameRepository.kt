package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow

interface GameRepository
{
    suspend fun getGames() : Flow<Resource<List<Game>>>

    suspend fun getGameById(id: Int) : Flow<Resource<Game>>

    suspend fun searchGame(searchQuery: String) : Flow<Resource<List<Game>>>
}