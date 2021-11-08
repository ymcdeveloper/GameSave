package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.db.model.GameDB
import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import javax.inject.Inject

interface SaveGameUseCase
{
    suspend operator fun invoke(game : GameDB)
}