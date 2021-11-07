package br.com.ymc.gamesave.domain.use_case.api_use_case

import br.com.ymc.gamesave.domain.repository.GameRepository
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import br.com.ymc.gamesave.util.handleError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry

import javax.inject.Inject

class GetGamesUseCaseImpl @Inject constructor(private val repository: GameRepository) : GetGamesUseCase
{
    override suspend operator fun invoke(): Flow<Resource<List<Game>>>
    {
        return flow {

            emit(Resource.Loading())

            val response = repository.getGames()

            if (response.isSuccessful)
            {
                emit(Resource.Success(response.body() ?: emptyList()))
            } else
            {
                emit(Resource.Error(response.code().handleError()))
            }

        }.retry(2) { e ->
            (e is Exception).also { if (it) delay(500) }
        }.catch { e ->
            emit(Resource.Error(e.handleError()))
        }
    }
}