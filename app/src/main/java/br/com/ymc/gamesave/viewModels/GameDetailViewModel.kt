package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.data.remote.dto.toGameDB
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGameDetailUseCase
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGameDetailUseCaseImpl
import br.com.ymc.gamesave.domain.use_case.db_use_case.*
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
    //LiveData values
    private val _game : MutableLiveData<Game> = MutableLiveData()
    val game : LiveData<Game> = _game

    private val _isGameAdded : MutableLiveData<Boolean> = MutableLiveData(false)
    val isGameAdded : LiveData<Boolean> = _isGameAdded

    private val _state : MutableLiveData<UIState> = MutableLiveData()
    var state : LiveData<UIState> = _state

    //Variables
    var gameDeleted : Boolean = false
    private var id = -1

    fun getGame(gameId : Int)
    {
        if(gameId != -1)
        {
            viewModelScope.launch {
                getGameDetailUseCase(gameId).collect { result ->
                    when(result)
                    {
                        is Resource.Success -> {
                            _game.postValue(result.data)
                            _state.postValue(UIState.Success)
                        }

                        is Resource.Error -> {
                            _state.postValue(UIState.Error(result.message ?: "Unknown Error"))
                        }

                        is Resource.Loading -> {
                            _state.postValue(UIState.Loading)
                        }
                    }
                }
            }
        }
    }

    fun getGameFromDB(gameId : Int)
    {
        if(gameId != -1)
        {
            viewModelScope.launch {
                getDBGameDetailUseCase(gameId).collect { result ->
                    when(result)
                    {
                        is Resource.Success -> {
                            _game.postValue(result.data)
                        }

                        is Resource.Error -> {
                            _state.postValue(UIState.Error(result.message ?: "Unknown Error"))
                        }

                        is Resource.Loading -> {
                            _state.postValue(UIState.Loading)
                        }
                    }
                }
            }
        }
    }

    fun insertGameToDB()
    {
        viewModelScope.launch {
            game.value?.let { saveGameUseCase(it.toGameDB()) }
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
            game.value?.let {
                deleteGameUseCase(it.toGameDB())
            }
        }
    }
}