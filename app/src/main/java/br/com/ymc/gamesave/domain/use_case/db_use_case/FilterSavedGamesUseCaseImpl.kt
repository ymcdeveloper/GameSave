package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilterSavedGamesUseCaseImpl @Inject constructor() : FilterSavedGamesUseCase
{
    override suspend operator fun invoke(games : List<Game>, searchQuery : String): Flow<Resource<List<Game>>>
    {
        return flow {
            val filteredGames = games.filter { game ->
                game.name.lowercase().contains(searchQuery.lowercase())
            }

            emit(Resource.Success(filteredGames))
        }
    }
}