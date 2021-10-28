package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import javax.inject.Inject

class CheckGameExistsUseCase @Inject constructor(private val repository: DatabaseRepository)
{
    suspend operator fun invoke(id : Int) : Boolean
    {
        return repository.checkGameExists(id)
    }
}