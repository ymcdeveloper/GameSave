package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ymc.gamesave.network.repository.ServiceRepository
import br.com.ymc.gamesave.util.BASE_URL
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class AllGamesViewModel @Inject constructor(private val serviceRepository: ServiceRepository) : ViewModel()
{
    fun callGamesApi()
    {
        viewModelScope.launch {
            serviceRepository.getGames()
        }

    }
}