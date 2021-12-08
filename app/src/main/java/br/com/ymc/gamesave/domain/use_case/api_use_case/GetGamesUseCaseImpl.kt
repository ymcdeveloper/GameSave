package br.com.ymc.gamesave.domain.use_case.api_use_case

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.core.util.handleError
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.domain.repository.GameRepository
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
        return repository.getGames()
    }
}