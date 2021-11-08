package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FilterSavedGamesUseCase
{
    suspend operator fun invoke(games : List<Game>, searchQuery : String): Flow<Resource<List<Game>>>
}