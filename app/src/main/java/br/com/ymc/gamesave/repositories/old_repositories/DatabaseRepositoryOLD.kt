package br.com.ymc.gamesave.repositories.old_repositories

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.local.dao.GameDAO
import br.com.ymc.gamesave.data.local.entity.toGame
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.data.remote.dto.toGameDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseRepositoryOLD @Inject constructor(private val gameDao: GameDAO)
{
    suspend fun addGame(game: Game)
    {
        gameDao.insertGame(game.toGameDB())
    }

    suspend fun selectGame(id: Int) : Flow<Resource<Game>>
    {
        return flow {
            try
            {
                emit(Resource.Loading())

                gameDao.selectGameById(id).let {
                    emit(Resource.Success(it.toGame()))
                }
            }
            catch (e: Exception)
            {
                emit(Resource.Error(e.localizedMessage ?: "Unknown Error in selectGame"))
            }
        }
    }

    suspend fun selectMyGames() : List<Game>
    {
//        val arrGamesReturn: MutableList<Game> = mutableListOf()

        val arrGamesDB = gameDao.selectSavedGames()

//        for (game in arrGamesDB)
//        {
//            arrGamesReturn.add(game.toGame())
//        }

        return arrGamesDB.map { it.toGame() }

//        arrGames.postValue(arrGamesReturn)
    }

    suspend fun checkGameExist(id: Int): Boolean
    {
        return gameDao.gameExists(id)
    }

    suspend fun deleteGame(game: Game)
    {
        gameDao.deleteGame(game.toGameDB())
    }
}