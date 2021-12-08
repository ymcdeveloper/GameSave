package br.com.ymc.gamesave.domain.use_case.api_use_case

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.remote.dto.Game
import kotlinx.coroutines.flow.Flow

interface SearchGameUseCase
{
    suspend operator fun invoke(searchQuery: String) : Flow<Resource<List<Game>>>
}