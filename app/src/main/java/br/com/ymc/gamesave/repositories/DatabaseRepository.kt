package br.com.ymc.gamesave.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.ymc.gamesave.db.dao.GameDAO
import br.com.ymc.gamesave.db.model.GameDB
import br.com.ymc.gamesave.db.model.toGame
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.model.toGameDB
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val gameDao: GameDAO)
{
    suspend fun addGame(game: Game)
    {
        gameDao.insertGame(game.toGameDB())
    }

    suspend fun selectGame(id: Int, arrGames: MutableLiveData<Game>)
    {
        gameDao.selectGameById(id)?.let {
            arrGames.postValue(it.toGame())
        }

    }

    suspend fun selectMyGames(arrGames: MutableLiveData<List<Game>>)
    {
        val arrGamesReturn: MutableList<Game> = mutableListOf()

        val arrGamesDB = gameDao.selectSavedGames()

        for (game in arrGamesDB)
        {
            arrGamesReturn.add(game.toGame())
        }

        arrGames.postValue(arrGamesReturn)
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