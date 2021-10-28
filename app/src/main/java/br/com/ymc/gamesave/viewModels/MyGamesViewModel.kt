package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.domain.use_case.db_use_case.FilterSavedGamesUseCase
import br.com.ymc.gamesave.domain.use_case.db_use_case.GetCountUseCase
import br.com.ymc.gamesave.domain.use_case.db_use_case.GetSavedGamesUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGamesViewModel @Inject constructor(private val getSavedGamesUseCase: GetSavedGamesUseCase,
                                           private val filterSavedGamesUseCase: FilterSavedGamesUseCase,
                                           private val getCountUseCase : GetCountUseCase) : ViewModel()
{
    private var _arrGames : MutableLiveData<List<Game>> = MutableLiveData()
    var arrGames : LiveData<List<Game>> = _arrGames

    private var _state : MutableLiveData<UIState> = MutableLiveData()
    var state : LiveData<UIState> = _state

    fun loadGames()
    {
        viewModelScope.launch {
            getSavedGamesUseCase().collect { result ->
                when(result)
                {
                    is Resource.Success -> {
                        _state.postValue(UIState.Success)
                        _arrGames.postValue(result.data)
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

    fun searchGame(searchText : String)
    {
        if(searchText.length <= 1)
        {
            loadGames()
            return
        }

        viewModelScope.launch {
            filterSavedGamesUseCase(_arrGames.value!!, searchText).collect {
                _arrGames.postValue(it.data)
            }
        }
    }

    fun shouldReload()
    {
        viewModelScope.launch {
            if(_arrGames.value?.size != getCountUseCase())
            {
                loadGames()
            }
        }
    }
}