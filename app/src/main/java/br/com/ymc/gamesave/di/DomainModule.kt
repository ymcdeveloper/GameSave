package br.com.ymc.gamesave.di

import br.com.ymc.gamesave.domain.use_case.api_use_case.*
import br.com.ymc.gamesave.domain.use_case.db_use_case.*
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

    @Binds
    fun bindCheckGameExistsUseCase(useCase: CheckGameExistsUseCaseImpl): CheckGameExistsUseCase

    @Binds
    fun bindDeleteGamesUseCase(useCase: DeleteGameUseCaseImpl): DeleteGameUseCase

    @Binds
    fun bindFilterSavedGamesUseCase(useCase: FilterSavedGamesUseCaseImpl): FilterSavedGamesUseCase

    @Binds
    fun bindGetCountUseCase(useCase: GetCountUseCaseImpl): GetCountUseCase

    @Binds
    fun bindGetDBGameDetailUseCase(useCase: GetDBGameDetailUseCaseImpl): GetDBGameDetailUseCase

    @Binds
    fun bindGetSavedGamesUseCase(useCase: GetSavedGamesUseCaseImpl): GetSavedGamesUseCase

    @Binds
    fun bindSaveGameUseCase(useCase: SaveGameUseCaseImpl): SaveGameUseCase
}