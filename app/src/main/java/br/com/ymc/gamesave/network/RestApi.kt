package br.com.ymc.gamesave.network

import br.com.ymc.gamesave.BuildConfig.CLIENT_ID
import br.com.ymc.gamesave.BuildConfig.TOKEN
import br.com.ymc.gamesave.model.Game
import retrofit2.Response
import retrofit2.http.*

interface RestApi
{
    @POST("games")
    suspend fun getGames( @Query("fields") bodyValues : String, @Header("Authorization") token: String = TOKEN, @Header("Client-ID") clientId: String = CLIENT_ID) : Response<List<Game>>

    @POST("games")
    suspend fun getGameById(@Query("fields") bodyValues : String, @Header("Authorization") token: String? = TOKEN, @Header("Client-ID") clientId: String? = CLIENT_ID) : Response<List<Game>>

    @POST("games")
    suspend fun searchGame(@Query("search") searchQuery : String, @Query("fields") bodyValues : String, @Header("Authorization") token: String? = TOKEN, @Header("Client-ID") clientId: String? = CLIENT_ID) : Response<List<Game>>
}