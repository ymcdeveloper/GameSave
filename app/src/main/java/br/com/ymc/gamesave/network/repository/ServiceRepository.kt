package br.com.ymc.gamesave.network.repository

import androidx.lifecycle.MutableLiveData
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.network.RestApi
import br.com.ymc.gamesave.util.Const
import javax.inject.Inject

class ServiceRepository @Inject constructor(private val restApi: RestApi)
{
    suspend fun getGames(arrGamesData : MutableLiveData<List<Game>>)
    {
        val response = restApi.getGames(Const.TOKEN, Const.CLIENT_ID, "name, cover.image_id, total_rating; limit 500; where total_rating != null; sort total_rating desc;")

        if(response.isSuccessful)
        {
            arrGamesData.postValue(response.body())
        }
    }

    suspend fun getGameById(id : Int, game : MutableLiveData<Game>)
    {
        val response = restApi.getGameById(Const.TOKEN, Const.CLIENT_ID, "cover.image_id, name, summary, platforms.abbreviation; where id = $id;")

        if(response.isSuccessful)
        {
            game.postValue(response.body()?.get(0))
        }
    }
}