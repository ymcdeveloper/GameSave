package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.db.model.GameDB
import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import javax.inject.Inject

class SaveGameUseCaseImpl @Inject constructor(private val repository: DatabaseRepository) : SaveGameUseCase
{
    override suspend operator fun invoke(game : GameDB)
    {
        repository.insertGame(game)
    }
}