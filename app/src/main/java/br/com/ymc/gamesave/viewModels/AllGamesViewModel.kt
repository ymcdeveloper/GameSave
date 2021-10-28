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
    private var _gamesListState: MutableLiveData<Resource<List<Game>>> = MutableLiveData()
    var arrGames: LiveData<Resource<List<Game>>> = _gamesListState

    private var searchJob: Job? = null


    fun callGamesApi()
    {
        viewModelScope.launch {
            getGamesUseCase().collect {
                _gamesListState.value = it
            }
        }
    }

    fun searchGame(query: String)
    {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            searchGamesUseCase("'$query'").collect {
                _gamesListState.value = it
            }
        }
    }
}