package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import javax.inject.Inject

interface CheckGameExistsUseCase
{
    suspend operator fun invoke(id : Int) : Boolean
}