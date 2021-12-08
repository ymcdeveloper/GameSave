package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.remote.dto.Game
import kotlinx.coroutines.flow.Flow

interface GetSavedGamesUseCase
{
    suspend operator fun invoke(): Flow<Resource<List<Game>>>
}