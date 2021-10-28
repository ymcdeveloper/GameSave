package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.domain.use_case.db_use_case.FilterSavedGamesUseCase
import br.com.ymc.gamesave.domain.use_case.db_use_case.GetSavedGamesUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGamesViewModel @Inject constructor(private val getSavedGamesUseCase: GetSavedGamesUseCase, private val filterSavedGamesUseCase: FilterSavedGamesUseCase) : ViewModel()
{
    private var _arrGames : MutableLiveData<Resource<List<Game>>> = MutableLiveData()
    var arrGames : LiveData<Resource<List<Game>>> = _arrGames

    fun loadGames()
    {
        viewModelScope.launch {
            getSavedGamesUseCase().collect {
                _arrGames.postValue(it)
            }
        }
    }

    fun searchGame(searchText : String)
    {
        if(searchText.length <= 1)
        {
            loadGames()
            return
        }

        viewModelScope.launch {
            filterSavedGamesUseCase(_arrGames.value?.data!!, searchText).collect {
                _arrGames.postValue(it)
            }
        }
    }
}