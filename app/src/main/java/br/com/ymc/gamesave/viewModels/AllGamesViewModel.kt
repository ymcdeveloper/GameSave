package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.network.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGamesViewModel @Inject constructor(private val serviceRepository: ServiceRepository) : ViewModel()
{
    private var _arrGames : MutableLiveData<List<Game>> = MutableLiveData()
    var arrGames = _arrGames

    fun callGamesApi()
    {
        viewModelScope.launch {
            serviceRepository.getGames(_arrGames)
        }
    }
}