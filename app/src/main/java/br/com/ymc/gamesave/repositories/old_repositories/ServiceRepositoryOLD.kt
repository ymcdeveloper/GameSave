package br.com.ymc.gamesave.repositories.old_repositories

import androidx.lifecycle.MutableLiveData
import br.com.ymc.gamesave.BuildConfig.CLIENT_ID
import br.com.ymc.gamesave.BuildConfig.TOKEN
import br.com.ymc.gamesave.data.remote.RestApi
import br.com.ymc.gamesave.data.remote.dto.Game
import java.net.UnknownHostException
import javax.inject.Inject

class ServiceRepositoryOLD @Inject constructor(private val restApi: RestApi)
{
    suspend fun getGames(arrGamesData : MutableLiveData<List<Game>>)
    {
        try
        {
            val response = restApi.getGames(TOKEN, CLIENT_ID, "name, cover.image_id; limit 500; where rating_count > 500 & total_rating > 60 & cover != null & category = 0 & summary != null; sort total_rating desc;")

            if(response.isSuccessful)
            {
                arrGamesData.postValue(response.body())
            }
        }
        catch (err : Exception)
        {
            when(err)
            {
                is UnknownHostException -> println("Erro na conexão com internet, e tá tudo bem")
                else -> println("Outro erro")
            }
        }
    }

    suspend fun getGameById(id : Int, game : MutableLiveData<Game>)
    {
        val response = restApi.getGameById(TOKEN, CLIENT_ID, "cover.image_id, name, summary, total_rating, first_release_date, platforms.abbreviation; where id = $id;")

        if(response.isSuccessful)
        {
            game.postValue(response.body()?.get(0))
        }
    }

    suspend fun getGameBySearch(searchQuery : String, arrGamesData : MutableLiveData<List<Game>>)
    {
        val response = restApi.searchGame(TOKEN, CLIENT_ID, searchQuery,"name, cover.image_id; limit 500; where category = 0;")

        if(response.isSuccessful)
        {
            arrGamesData.postValue(response.body())
        }
    }
}