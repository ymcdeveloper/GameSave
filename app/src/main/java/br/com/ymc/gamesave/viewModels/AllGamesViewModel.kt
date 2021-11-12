package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCase
import br.com.ymc.gamesave.domain.use_case.api_use_case.SearchGameUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGamesViewModel @Inject constructor(private val getGamesUseCase: GetGamesUseCase, private val searchGamesUseCase: SearchGameUseCase) : ViewModel()
{
    private var _gamesList: MutableLiveData<List<Game>> = MutableLiveData()
    var gamesList: LiveData<List<Game>> = _gamesList

    private var _state: MutableLiveData<UIState> = MutableLiveData()
    var state: LiveData<UIState> = _state

    private var searchJob: Job? = null


    fun callGamesApi() = viewModelScope.launch {
        getGamesUseCase().collect { result ->
            when (result)
            {
                is Resource.Success ->
                {
//                    EspressoIdlingResource.decrement()
                    _gamesList.value = result.data
                    _state.postValue(UIState.Success)
                }
                is Resource.Error ->
                {
                    _gamesList.value = listOf()
                    _state.postValue(UIState.Error(result.message ?: "Unknown error"))
                }
                is Resource.Loading ->
                {
//                    EspressoIdlingResource.increment()
                    _state.postValue(UIState.Loading)
                }
                else -> Unit
            }
        }
    }

    fun searchGame(query: String)
    {
//        EspressoIdlingResource.increment()
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            searchGamesUseCase("'$query'").collect { result ->
                when (result)
                {
                    is Resource.Success ->
                    {
                        _gamesList.value = result.data
                        _state.postValue(UIState.Success)
//                        EspressoIdlingResource.decrement()
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
}

sealed class UIState
{
    object Success : UIState()
    object Loading : UIState()
    data class Error(val message: String) : UIState()
}