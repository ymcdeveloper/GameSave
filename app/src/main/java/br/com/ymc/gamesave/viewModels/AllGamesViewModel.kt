package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.repositories.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGamesViewModel @Inject constructor(private val serviceRepository: ServiceRepository) : ViewModel()
{
    private var _arrGames : MutableLiveData<List<Game>> = MutableLiveData()
    var arrGames = _arrGames

    private var searchJob: Job? = null

    fun callGamesApi()
    {
        viewModelScope.launch {
            serviceRepository.getGames(_arrGames)
        }
    }

    fun searchGame(query : String)
    {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            serviceRepository.getGameBySearch("'$query'", _arrGames)
        }
    }
}