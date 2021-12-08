package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.core.util.handleError
import br.com.ymc.gamesave.data.remote.RestApi
import br.com.ymc.gamesave.data.remote.dto.Game
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import retrofit2.Response
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val api: RestApi) : GameRepository
{
    override suspend fun getGames(): Flow<Resource<List<Game>>> = flow {

        emit(Resource.Loading())

        val response = api.getGames(
            bodyValues = "name, cover.image_id; limit 500; where rating_count > 200 & total_rating > 60 & cover != null & category = 0 & summary != null; sort total_rating desc;"
        )

        if (response.isSuccessful)
        {
            emit(Resource.Success(response.body() ?: emptyList()))
        }
        else
        {
            emit(Resource.Error(response.code().handleError()))
        }
    }.retry(2) { e ->
        (e is Exception).also { if (it) delay(500) }
    }.catch { e ->
        emit(Resource.Error(e.handleError()))
    }


    override suspend fun getGameById(id: Int): Flow<Resource<Game>> = flow {
        emit(Resource.Loading())

        val response = api.getGameById(
            "cover.image_id, name, summary, total_rating, first_release_date, platforms.abbreviation; where id = $id;"
        )

        if (response.isSuccessful)
        {
            response.body().let {
                if (it != null)
                {
                    if(it.isNotEmpty())
                    {
                        emit(Resource.Success(it[0]))
                    }
                }
                else
                {
                    emit(Resource.Error("Game not found, try again later"))
                }
            }
        }
        else
        {
            emit(Resource.Error(response.code().handleError()))
        }

    }.retry(2) { e ->
        (e is Exception).also { if (it) delay(500) }
    }.catch { e ->
        emit(Resource.Error(e.handleError()))
    }

    override suspend fun searchGame(searchQuery: String): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading())

        val response = api.searchGame(
            searchQuery, "name, cover.image_id; limit 500; where category = 0;"
        )

        if (response.isSuccessful)
        {
            emit(Resource.Success(response.body() ?: emptyList()))
        }
        else
        {
            emit(Resource.Error(response.code().handleError()))
        }

    }.retry(2) { e ->
        (e is Exception).also { if (it) delay(500) }
    }.catch { e ->
        emit(Resource.Error(e.handleError()))
    }
}