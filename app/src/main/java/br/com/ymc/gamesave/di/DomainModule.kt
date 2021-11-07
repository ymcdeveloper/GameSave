package br.com.ymc.gamesave.di

import br.com.ymc.gamesave.domain.use_case.api_use_case.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule
{
    @Binds
    fun bindGetGamesUseCase(useCase: GetGamesUseCaseImpl): GetGamesUseCase

    @Binds
    fun bindSearchGamesUseCase(useCase: SearchGameUseCaseImpl): SearchGameUseCase

    @Binds
    fun bindGetGameDetailUseCase(useCase: GetGameDetailUseCaseImpl): GetGameDetailUseCase
}