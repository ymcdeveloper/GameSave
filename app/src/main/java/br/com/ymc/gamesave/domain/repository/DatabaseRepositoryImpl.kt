package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.local.dao.GameDAO
import br.com.ymc.gamesave.data.local.entity.GameDB
import br.com.ymc.gamesave.data.local.entity.toGame
import br.com.ymc.gamesave.data.remote.dto.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val gameDAO: GameDAO) : DatabaseRepository
{
    override suspend fun getSavedGames(): Flow<Resource<List<Game>>> = flow {

        emit(Resource.Loading())

        val games = gameDAO.selectSavedGames().map { it.toGame() }

        emit(Resource.Success(games))
    }.catch { e ->
        emit(Resource.Error(e.localizedMessage ?: "Error to get saved games."))
    }

    override suspend fun getGame(id: Int): Flow<Resource<Game>> = flow {

        emit(Resource.Loading())

        val game = gameDAO.selectGameById(id).toGame()

        emit(Resource.Success(game))
    }.catch { e ->
        emit(Resource.Error(e.localizedMessage ?: "Error to get saved games."))
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

    override suspend fun getCount(): Int
    {
        return gameDAO.getCount()
    }

}