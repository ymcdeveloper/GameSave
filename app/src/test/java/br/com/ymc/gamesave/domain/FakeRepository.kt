package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.local.entity.GameDB
import br.com.ymc.gamesave.data.remote.dto.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : GameRepository
{
    private var games = listOf<Game>(
        Game(null, 1, "test1", "test Summary", 10f, null, ""),
        Game(null, 2, "test2", "test Summary", 30f, null, ""),
        Game(null, 3, "test3", "test Summary", 50f, null, ""),
        Game(null, 4, "test4", "test Summary", 70f, null, ""),
        Game(null, 5, "test5", "test Summary", 90f, null, ""),
        Game(null, 5, "test6", "test Summary", 100f, null, "")
    )

    private var shouldReturnError = false

    fun setReturnError(value: Boolean)
    {
        shouldReturnError = value
    }

    override suspend fun getGames(): Flow<Resource<List<Game>>>
    {
        return flow {
            emit(Resource.Loading())

            if(shouldReturnError)
            {
                emit(Resource.Error("Test exception"))
            }
            else
            {
                emit(Resource.Success(games))
            }
        }
    }

    override suspend fun getGameById(id: Int): Flow<Resource<Game>>
    {
        return flow {
            emit(Resource.Loading())

            if(shouldReturnError)
            {
                emit(Resource.Error("Test exception"))
                return@flow
            }
            else
            {
                val game = games.first { it.id == id }

                emit(Resource.Success(game))
                return@flow
            }
        }
    }

    override suspend fun searchGame(searchQuery: String): Flow<Resource<List<Game>>>
    {
        return flow {
            emit(Resource.Loading())

            if(shouldReturnError)
            {
                emit(Resource.Error("Test exception"))
                return@flow
            }
            else
            {
                val filteredGames = games.filter { it.name.contains("searchQuery") }

                emit(Resource.Success(filteredGames))
                return@flow
            }
        }
    }
}