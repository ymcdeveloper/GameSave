package br.com.ymc.gamesave.di

import android.content.Context
import br.com.ymc.gamesave.data.local.AppDatabase
import br.com.ymc.gamesave.data.local.dao.GameDAO
import br.com.ymc.gamesave.domain.repository.DatabaseRepository
import br.com.ymc.gamesave.domain.repository.DatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule
{
    @Provides
    @Singleton
    fun getAppDatabase(@ApplicationContext context : Context) : AppDatabase
    {
        return AppDatabase.getDBInstance(context)
    }

    @Provides
    fun gameDao(appDatabase: AppDatabase): GameDAO
    {
        return appDatabase.gameDao()
    }

    @Provides
    fun provideDatabaseRepository(gameDAO: GameDAO): DatabaseRepository
    {
        return DatabaseRepositoryImpl(gameDAO)
    }
}