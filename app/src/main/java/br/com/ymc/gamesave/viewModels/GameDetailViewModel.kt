package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.repositories.DatabaseRepository
import br.com.ymc.gamesave.repositories.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(private val repository: ServiceRepository, private val dbRepository: DatabaseRepository) : ViewModel()
{
    private val _game : MutableLiveData<Game> = MutableLiveData()
    val game : LiveData<Game> = _game

    private val _isGameAdded : MutableLiveData<Boolean> = MutableLiveData(false)
    val isGameAdded : LiveData<Boolean> = _isGameAdded

    var gameDeleted : Boolean = false

    var id = -1

    fun getGame(gameId : Int)
    {
        if(gameId != -1)
        {
            viewModelScope.launch {
                repository.getGameById(gameId, _game)
            }
        }
    }

    fun getGameFromDB(gameId : Int)
    {
        if(gameId != -1)
        {
            viewModelScope.launch {
                dbRepository.selectGame(gameId, _game)
            }
        }
    }

    fun insertGameToDB()
    {
        viewModelScope.launch {
            game.value?.let { dbRepository.addGame(it) }
        }
    }

    fun checkGameAdded(gameId : Int)
    {
        viewModelScope.launch {
            _isGameAdded.value = dbRepository.checkGameExist(gameId)
        }
    }

    fun deleteGame()
    {
        viewModelScope.launch {
            game.value?.let {
                dbRepository.deleteGame(it)
            }
        }
    }
}