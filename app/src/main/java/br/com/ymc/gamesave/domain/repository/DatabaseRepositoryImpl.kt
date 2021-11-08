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
    override suspend fun getSavedGames(): List<Game>
    {
        return gameDAO.selectSavedGames().map { it.toGame() }
    }

    override suspend fun getGame(id: Int): Game
    {
        return gameDAO.selectGameById(id).toGame()
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