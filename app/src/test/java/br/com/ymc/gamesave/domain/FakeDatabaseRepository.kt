package br.com.ymc.gamesave.domain

import androidx.annotation.VisibleForTesting
import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.local.entity.GameDB
import br.com.ymc.gamesave.data.local.entity.toGame
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDatabaseRepository : DatabaseRepository
{
    var games = mutableListOf<GameDB>()

    private var shouldReturnError = false

    fun setReturnError(value: Boolean)
    {
        shouldReturnError = value
    }

    override suspend fun getSavedGames(): Flow<Resource<List<Game>>>
    {
        return flow {
            if(shouldReturnError)
            {
                emit(Resource.Error("Test exception"))
            }
            else
            {
                emit(Resource.Success(games.map { it.toGame() }))
            }
        }
    }

    override suspend fun getGame(id: Int): Flow<Resource<Game>>
    {
        return flow {
            if(shouldReturnError)
            {
                emit(Resource.Error("Error"))
            }
            else
            {
                val game = games.find { it.id == id }

                game?.let {
                    emit(Resource.Success(it.toGame()))
                    return@flow
                }

                emit(Resource.Error("Error"))
            }
        }
    }

    override suspend fun insertGame(game: GameDB)
    {
        games.add(game)
    }

    override suspend fun removeGameFromLibrary(game: GameDB)
    {
        games.remove(game)
    }

    override suspend fun checkGameExists(id: Int): Boolean
    {
        val game = games.first { it.id == id }

        print(game.id)

        return false
    }

    override suspend fun getCount(): Int
    {
        return games.size
    }

    @VisibleForTesting
    fun addGame(game: GameDB)
    {
        games.add(game)
    }

}