package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.db.model.GameDB
import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DeleteGameUseCase
{
    suspend operator fun invoke(game : GameDB)
}