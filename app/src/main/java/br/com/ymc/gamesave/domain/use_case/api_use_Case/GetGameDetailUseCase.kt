package br.com.ymc.gamesave.domain.use_case.api_use_Case

import br.com.ymc.gamesave.domain.repository.GameRepository
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameDetailUseCase @Inject constructor(private val repository: GameRepository)
{
    suspend operator fun invoke(id: Int): Flow<Resource<Game>>
    {
        return repository.getGameById(id)
    }
}