package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.data.local.entity.GameDB

interface DeleteGameUseCase
{
    suspend operator fun invoke(game : GameDB)
}