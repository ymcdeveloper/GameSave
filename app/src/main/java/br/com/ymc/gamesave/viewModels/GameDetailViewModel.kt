package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.domain.use_case.api_use_Case.GetGameDetailUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.repositories.DatabaseRepository
import br.com.ymc.gamesave.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(private val getGameDetailUseCase: GetGameDetailUseCase, private val dbRepository: DatabaseRepository) : ViewModel()
{
    private val _gameState : MutableLiveData<Resource<Game>> = MutableLiveData()
    val game : LiveData<Resource<Game>> = _gameState

    private val _isGameAdded : MutableLiveData<Boolean> = MutableLiveData(false)
    val isGameAdded : LiveData<Boolean> = _isGameAdded

    var gameDeleted : Boolean = false

    var id = -1

    fun getGame(gameId : Int)
    {
        if(gameId != -1)
        {
            viewModelScope.launch {
                getGameDetailUseCase(gameId).collect {
                    _gameState.value = it
                }
            }
        }
    }

    fun getGameFromDB(gameId : Int)
    {
        if(gameId != -1)
        {
            viewModelScope.launch {
                dbRepository.selectGame(gameId).collect {
                    _gameState.value = it
                }
            }
        }
    }

    fun insertGameToDB()
    {
        viewModelScope.launch {
            game.value?.let { resourceData ->
                resourceData.data?.let { game ->
                    dbRepository.addGame(game)
                }
            }
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
//                dbRepository.deleteGame(it)
            }
        }
    }
}

sealed class GameDetailState
{
    object Init : GameDetailState()
    data class IsLoading(val isLoading: Boolean) : GameDetailState()
    data class Success(val arrGames: Game) : GameDetailState()
    data class Error(val message: String) : GameDetailState()
}