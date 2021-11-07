package br.com.ymc.gamesave.domain.repository

import androidx.lifecycle.LiveData
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GameRepository
{
    suspend fun getGames() : Response<List<Game>>

    suspend fun getGameById(id: Int) : Response<List<Game>>

    suspend fun searchGame(searchQuery: String) : Response<List<Game>>
}