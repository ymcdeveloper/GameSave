package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.repositories.DatabaseRepository
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

    fun searchGame(searchTex : String)
    {
        val arrGamesFiltered: List<Game> = _arrGames.value!!.filter {
            it.name.lowercase().contains(searchTex.lowercase())
        }

        if(searchTex.length <= 1)
        {
            loadGames()
            return
        }

        arrGames.postValue(arrGamesFiltered)
    }
}