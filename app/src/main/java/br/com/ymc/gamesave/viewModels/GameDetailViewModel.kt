package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.network.repository.DatabaseRepository
import br.com.ymc.gamesave.network.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(private val repository: ServiceRepository, private val dbRepository: DatabaseRepository) : ViewModel()
{
    private val _game : MutableLiveData<Game> = MutableLiveData()
    val game = _game

    private val _isGameAdded : MutableLiveData<Boolean> = MutableLiveData()
    val isGameAdded = _isGameAdded

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

    fun insertGameToDB(game : Game)
    {
        viewModelScope.launch {
            dbRepository.addGame(game)
        }

    }

    fun checkGameAdded(gameId : Int)
    {
        viewModelScope.launch {
            _isGameAdded.value = dbRepository.checkGameExist(gameId)
        }
    }
}