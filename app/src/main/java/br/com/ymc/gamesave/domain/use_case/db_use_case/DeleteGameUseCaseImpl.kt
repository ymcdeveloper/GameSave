package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.data.local.entity.GameDB
import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteGameUseCaseImpl @Inject constructor(private val repository: DatabaseRepository) : DeleteGameUseCase
{
    override suspend operator fun invoke(game : GameDB)
    {
        repository.removeGameFromLibrary(game)
    }
}