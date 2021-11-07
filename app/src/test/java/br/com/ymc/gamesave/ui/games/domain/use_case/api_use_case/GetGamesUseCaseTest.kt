package br.com.ymc.gamesave.ui.games.domain.use_case.api_use_case

import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetGamesUseCaseTest : GetGamesUseCase
{
    private var shouldReturnError = false

    fun setShouldReturnError(value : Boolean)
    {
        shouldReturnError = value
    }

    override suspend fun invoke(): Flow<Resource<List<Game>>>
    {
        return flow {
            if(shouldReturnError)
            {
                Resource.Error("Error", null)
            }
            else
            {
                Resource.Success(listOf(Game(null, 1, "test", "Test sm", null, null, null)))
            }
        }
    }

}