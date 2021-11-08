package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSavedGamesUseCaseImpl @Inject constructor(private val repository: DatabaseRepository) : GetSavedGamesUseCase
{
    override suspend operator fun invoke(): Flow<Resource<List<Game>>>
    {
        return flow {
            try
            {
                emit(Resource.Loading())
                val games = repository.getSavedGames()
                emit(Resource.Success(games))
            }
            catch (e: Exception)
            {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error in getSavedGames"))
            }
        }
    }
}