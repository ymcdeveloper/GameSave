package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.remote.dto.Game
import kotlinx.coroutines.flow.Flow

interface FilterSavedGamesUseCase
{
    suspend operator fun invoke(games : List<Game>, searchQuery : String): Flow<Resource<List<Game>>>
}