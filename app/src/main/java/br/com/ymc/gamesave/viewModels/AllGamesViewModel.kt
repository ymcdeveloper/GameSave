package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.domain.use_case.GetGamesUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGamesViewModel @Inject constructor(private val getGamesUseCase: GetGamesUseCase) : ViewModel()
{
    private var _gamesListState : MutableStateFlow<GamesListState> = MutableStateFlow(GamesListState.Init)
    var arrGames: StateFlow<GamesListState> = _gamesListState

    private var searchJob: Job? = null


    fun callGamesApi()
    {
        viewModelScope.launch {
            getGamesUseCase().onStart {
                _gamesListState.value = GamesListState.IsLoading(true)
            }.catch { exception ->
                _gamesListState.value = GamesListState.Error(exception.localizedMessage ?: "Unknown error")

            }.collect {  result ->
                _gamesListState.value = GamesListState.IsLoading(false)

                when(result)
                {
                    is Resource.Success -> {
                        _gamesListState.value = GamesListState.IsLoading(false)
                        _gamesListState.value = GamesListState.Success(result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _gamesListState.value = GamesListState.IsLoading(false)
                        _gamesListState.value = GamesListState.Error(result.message ?: "Unknown error")
                    }

                    is Resource.Loading -> {
                        _gamesListState.value = GamesListState.IsLoading(true)
                    }
                }
            }
        }
    }

    fun searchGame(query : String)
    {
//        searchJob?.cancel()
//        searchJob = viewModelScope.launch {
//            delay(500)
//            serviceRepository.getGameBySearch("'$query'", _gamesListState)
//        }
    }
}

sealed class GamesListState
{
    object Init : GamesListState()
    data class IsLoading(val isLoading: Boolean) : GamesListState()
    data class Success(val arrGames: List<Game>) : GamesListState()
    data class Error(val message: String) : GamesListState()
}