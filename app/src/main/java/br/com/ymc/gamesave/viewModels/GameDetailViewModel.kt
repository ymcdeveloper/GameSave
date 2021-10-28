package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGameDetailUseCase
import br.com.ymc.gamesave.domain.use_case.db_use_case.CheckGameExistsUseCase
import br.com.ymc.gamesave.domain.use_case.db_use_case.DeleteGameUseCase
import br.com.ymc.gamesave.domain.use_case.db_use_case.GetDBGameDetailUseCase
import br.com.ymc.gamesave.domain.use_case.db_use_case.SaveGameUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.model.toGameDB
import br.com.ymc.gamesave.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(private val getGameDetailUseCase: GetGameDetailUseCase,
                                              private val getDBGameDetailUseCase: GetDBGameDetailUseCase,
                                              private val deleteGameUseCase: DeleteGameUseCase,
                                              private val saveGameUseCase: SaveGameUseCase,
                                              private val checkGameExistsUseCase: CheckGameExistsUseCase) : ViewModel()
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
                getDBGameDetailUseCase(gameId).collect {
                    _gameState.value = it
                }
            }
        }
    }

    fun insertGameToDB()
    {
        viewModelScope.launch {
            game.value?.data?.let { saveGameUseCase(it.toGameDB()) }
        }
    }

    fun checkGameAdded(gameId : Int)
    {
        viewModelScope.launch {
            _isGameAdded.value = checkGameExistsUseCase(gameId)
        }
    }

    fun deleteGame()
    {
        viewModelScope.launch {
            game.value?.data?.let {
                deleteGameUseCase(it.toGameDB())
            }
        }
    }
}