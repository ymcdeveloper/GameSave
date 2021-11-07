package br.com.ymc.gamesave.domain.use_case.api_use_case

import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchGameUseCase
{
    suspend operator fun invoke(searchQuery: String) : Flow<Resource<List<Game>>>
}