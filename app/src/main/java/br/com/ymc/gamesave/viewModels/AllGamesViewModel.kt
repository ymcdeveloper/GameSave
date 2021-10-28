package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.*
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCase
import br.com.ymc.gamesave.domain.use_case.api_use_case.SearchGameUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGamesViewModel @Inject constructor(private val getGamesUseCase: GetGamesUseCase, private val searchGamesUseCase: SearchGameUseCase) : ViewModel()
{
    private var _gamesListState: MutableLiveData<List<Game>> = MutableLiveData()
    var arrGames: LiveData<List<Game>> = _gamesListState

    private var _state: MutableLiveData<UIState> = MutableLiveData()
    var state: LiveData<UIState> = _state

    private var searchJob: Job? = null


    fun callGamesApi()
    {
        viewModelScope.launch {
            getGamesUseCase().collect { result ->
                when(result)
                {
                    is Resource.Success -> {
                        _gamesListState.value = result.data
                        _state.postValue(UIState.Success)
                    }
                    is Resource.Error ->
                    {
                        _state.postValue(UIState.Error(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading ->
                    {
                        _state.postValue(UIState.Loading)
                    }
                }
            }
        }
    }

    fun searchGame(query: String)
    {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
//            _isLoading.value = true
            searchGamesUseCase("'$query'").collect {
                _gamesListState.value = it.data
            }
        }
    }
}

sealed class UIState
{
    object Success : UIState()
    object Loading : UIState()
    data class Error(val message: String) : UIState()
}