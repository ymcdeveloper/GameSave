package br.com.ymc.gamesave.domain.use_case.api_use_case

import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetGameDetailUseCase
{
    suspend operator fun invoke(id: Int) : Flow<Resource<Game>>
}