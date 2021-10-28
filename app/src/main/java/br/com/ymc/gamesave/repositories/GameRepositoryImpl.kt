package br.com.ymc.gamesave.repositories

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import br.com.ymc.gamesave.domain.repository.GameRepository
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.network.RestApi
import br.com.ymc.gamesave.util.Const
import br.com.ymc.gamesave.util.Resource
import br.com.ymc.gamesave.util.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val api : RestApi) : GameRepository
{
    override suspend fun getGames(): Flow<Resource<List<Game>>>
    {
        return flow {
            try
            {
                emit(Resource.Loading())

                val response = api.getGames(Const.TOKEN, Const.CLIENT_ID, "name, cover.image_id; limit 500; where rating_count > 200 & total_rating > 60 & cover != null & category = 0 & summary != null; sort total_rating desc;")

                if(response.isSuccessful)
                {
                    emit(Resource.Success(response.body() ?: emptyList()))
                }
                else
                {
                    emit(Resource.Error(response.code().handleError()))
                }
            }
            catch (e: Exception)
            {
                emit(Resource.Error(e.handleError()))
            }
        }
    }

    override suspend fun getGameById(id: Int): Flow<Resource<Game>>
    {
        return flow {
            try
            {
                emit(Resource.Loading())

                val response = api.getGameById(Const.TOKEN, Const.CLIENT_ID, "cover.image_id, name, summary, total_rating, first_release_date, platforms.abbreviation; where id = $id;")

                if(response.isSuccessful)
                {
                    emit(Resource.Success(response.body()!![0]))
                }
                else
                {
                    emit(Resource.Error(response.code().handleError()))
                }
            }
            catch (e: Exception)
            {
                emit(Resource.Error(e.handleError()))
            }
        }
    }

    override suspend fun searchGame(searchQuery: String): Flow<Resource<List<Game>>>
    {
        return flow {
            try
            {
                emit(Resource.Loading())

                val response = api.searchGame(Const.TOKEN, Const.CLIENT_ID, searchQuery, "name, cover.image_id; limit 500; where category = 0;")

                if(response.isSuccessful)
                {
                    emit(Resource.Success(response.body() ?: emptyList()))
                }
                else
                {
                    emit(Resource.Error(response.code().handleError()))
                }
            }
            catch (e: Exception)
            {
                emit(Resource.Error(e.handleError()))
            }
        }
    }
}