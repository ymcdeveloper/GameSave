package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSavedGamesUseCaseImpl @Inject constructor(private val repository: DatabaseRepository) : GetSavedGamesUseCase
{
    override suspend operator fun invoke(): Flow<Resource<List<Game>>>
    {
        return repository.getSavedGames()
    }
}