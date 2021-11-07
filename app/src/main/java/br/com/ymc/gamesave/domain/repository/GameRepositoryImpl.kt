package br.com.ymc.gamesave.domain.repository

import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.network.RestApi
import retrofit2.Response
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val api: RestApi) : GameRepository
{
    override suspend fun getGames(): Response<List<Game>>
    {
        return api.getGames(
            bodyValues = "name, cover.image_id; limit 500; where rating_count > 200 & total_rating > 60 & cover != null & category = 0 & summary != null; sort total_rating desc;"
        )
    }

    override suspend fun getGameById(id: Int): Response<List<Game>>
    {
        return api.getGameById(
            "cover.image_id, name, summary, total_rating, first_release_date, platforms.abbreviation; where id = $id;"
        )
    }

    override suspend fun searchGame(searchQuery: String): Response<List<Game>>
    {
        return api.searchGame(
            searchQuery,
            "name, cover.image_id; limit 500; where category = 0;"
        )
    }
}