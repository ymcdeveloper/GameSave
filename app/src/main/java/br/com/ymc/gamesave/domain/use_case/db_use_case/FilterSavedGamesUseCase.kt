package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import br.com.ymc.gamesave.domain.repository.GameRepository
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterSavedGamesUseCase @Inject constructor(private val repository: DatabaseRepository)
{
    suspend operator fun invoke(games : List<Game>, searchQuery : String): Flow<Resource<List<Game>>>
    {
        return repository.filterGames(games, searchQuery)
    }
}