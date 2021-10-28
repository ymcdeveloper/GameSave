package br.com.ymc.gamesave.domain.use_case.api_use_case

import br.com.ymc.gamesave.domain.repository.GameRepository
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class SearchGameUseCase @Inject constructor(private val repository: GameRepository)
{
    suspend operator fun invoke(searchQuery : String) : Flow<Resource<List<Game>>>
    {
        return repository.searchGame(searchQuery)
    }
}