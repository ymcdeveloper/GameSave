package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.network.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGamesViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel()
{
    private var _arrGames : MutableLiveData<List<Game>> = MutableLiveData()
    var arrGames = _arrGames

    fun loadGames()
    {
        viewModelScope.launch {
            repository.selectMyGames(_arrGames)
        }
    }
}