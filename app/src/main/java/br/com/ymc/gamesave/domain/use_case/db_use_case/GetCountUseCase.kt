package br.com.ymc.gamesave.domain.use_case.db_use_case

interface GetCountUseCase
{
    suspend operator fun invoke() : Int
}