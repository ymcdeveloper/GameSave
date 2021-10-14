package br.com.ymc.gamesave.network.repository

import br.com.ymc.gamesave.network.RestApi
import br.com.ymc.gamesave.util.CLIENT_ID
import br.com.ymc.gamesave.util.TOKEN
import retrofit2.await
import retrofit2.awaitResponse
import javax.inject.Inject

class ServiceRepository @Inject constructor(private val restApi: RestApi)
{
    suspend fun getGames()
    {
        val response = restApi.getGames(TOKEN, CLIENT_ID, "name, cover.image_id, total_rating; limit 500; where total_rating != null; sort total_rating desc;")
//        val response = restApi.getGames(TOKEN, CLIENT_ID, "name")
//        val response = restApi.getGames(TOKEN, CLIENT_ID, 500)

        if(response.isSuccessful)
        {
            for(game in response.body()!!)
            {
                println(game.name)
            }
        }
    }
}