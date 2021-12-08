package br.com.ymc.gamesave.domain.use_case.api_use_case

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.remote.dto.Game
import kotlinx.coroutines.flow.Flow

interface GetGameDetailUseCase
{
    suspend operator fun invoke(id: Int) : Flow<Resource<Game>>
}