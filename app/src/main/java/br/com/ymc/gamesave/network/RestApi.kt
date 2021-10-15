package br.com.ymc.gamesave.network

import br.com.ymc.gamesave.model.Game
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RestApi
{
    @POST("games")
    suspend fun getGames(@Header("Authorization") token: String, @Header("Client-ID") clientId: String, @Query("fields") bodyValues : String) : Response<List<Game>>

    @POST("games")
    suspend fun getGameById(@Header("Authorization") token: String, @Header("Client-ID") clientId: String, @Query("fields") bodyValues : String) : Response<List<Game>>
}