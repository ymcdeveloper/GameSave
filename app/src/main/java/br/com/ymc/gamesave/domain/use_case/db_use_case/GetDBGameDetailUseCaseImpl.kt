package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDBGameDetailUseCaseImpl @Inject constructor(private val repository: DatabaseRepository) : GetDBGameDetailUseCase
{
    override suspend operator fun invoke(id: Int): Flow<Resource<Game>>
    {
        return repository.getGame(id)
    }
}