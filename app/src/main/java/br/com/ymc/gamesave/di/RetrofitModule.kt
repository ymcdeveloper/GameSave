package br.com.ymc.gamesave.di

import br.com.ymc.gamesave.domain.repository.GameRepository
import br.com.ymc.gamesave.network.RestApi
import br.com.ymc.gamesave.domain.repository.GameRepositoryImpl
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCase
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCaseImpl
import br.com.ymc.gamesave.util.Const
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule
{
    @Provides
    @Singleton
    fun getRetrofitRestInstance(retrofit: Retrofit) : RestApi
    {
        return retrofit.create(RestApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGameRepository(api: RestApi): GameRepository
    {
        return GameRepositoryImpl(api)
    }
}