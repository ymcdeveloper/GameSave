package br.com.ymc.gamesave.domain.use_case.db_use_case

import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetCountUseCaseImpl @Inject constructor(private val repository: DatabaseRepository) : GetCountUseCase
{
    override suspend operator fun invoke() : Int
    {
        return repository.getCount()
    }
}