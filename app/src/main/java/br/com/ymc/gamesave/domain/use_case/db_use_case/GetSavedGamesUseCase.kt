package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetSavedGamesUseCase
{
    suspend operator fun invoke(): Flow<Resource<List<Game>>>
}