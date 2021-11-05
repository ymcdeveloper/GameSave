package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.db.dao.GameDAO
import br.com.ymc.gamesave.db.model.GameDB
import br.com.ymc.gamesave.db.model.toGame
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val gameDAO: GameDAO) : DatabaseRepository
{
    override suspend fun getSavedGames(): Flow<Resource<List<Game>>>
    {
        return flow {
            try
            {
                emit(Resource.Loading())
                val games = gameDAO.selectSavedGames().map { it.toGame() }
                emit(Resource.Success(games))
            }
            catch (e: Exception)
            {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error in getSavedGames"))
            }
        }
    }

    override suspend fun getGame(id: Int): Flow<Resource<Game>>
    {
        return flow {
            try
            {
                emit(Resource.Loading())
                val game = gameDAO.selectGameById(id).toGame()
                emit(Resource.Success(game))
            }
            catch (e: Exception)
            {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error in getSavedGames"))
            }
        }
    }

    override suspend fun insertGame(game: GameDB)
    {
        gameDAO.insertGame(game)
    }

    override suspend fun removeGameFromLibrary(game: GameDB)
    {
        gameDAO.deleteGame(game)
    }

    override suspend fun checkGameExists(id: Int) : Boolean
    {
        return gameDAO.gameExists(id)
    }

    override suspend fun filterGames(games : List<Game>, filterQuery: String): Flow<Resource<List<Game>>>
    {
        return flow {
            val filteredGames = games.filter { game ->
                game.name.lowercase().contains(filterQuery.lowercase())
            }

            emit(Resource.Success(filteredGames))
        }
    }

    override suspend fun getCount(): Int
    {
        return gameDAO.getCount()
    }

}