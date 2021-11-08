package br.com.ymc.gamesave.ui.games.domain.repository
//
//import androidx.lifecycle.MutableLiveData
//import br.com.ymc.gamesave.db.model.GameDB
//import br.com.ymc.gamesave.db.model.toGame
//import br.com.ymc.gamesave.domain.repository.DatabaseRepository
//import br.com.ymc.gamesave.domain.repository.GameRepository
//import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCase
//import br.com.ymc.gamesave.model.Game
//import br.com.ymc.gamesave.util.Resource
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import retrofit2.Response
//
//class FakeGameRepository : DatabaseRepository, GetGamesUseCase
//{
//    private val games = mutableListOf<GameDB>()
//
//    private val observableGameItems = MutableLiveData(games.map { it.toGame() })
//
//    private var shouldReturnError = false
//
//    fun setShouldReturnError(value: Boolean)
//    {
//        shouldReturnError = value
//    }
//
//    private fun refreshLiveData()
//    {
//        observableGameItems.postValue(games.map { it.toGame() })
//    }
//
//    override suspend fun getSavedGames(): List<Game>
//    {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getGame(id: Int): Game
//    {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun insertGame(game: GameDB)
//    {
//        games.add(game)
//    }
//
//    override suspend fun removeGameFromLibrary(game: GameDB)
//    {
//        games.remove(game)
//        refreshLiveData()
//    }
//
//    override suspend fun checkGameExists(id: Int): Boolean
//    {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getCount(): Int
//    {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun invoke(): Flow<Resource<List<Game>>>
//    {
//        return flow {
//            if (shouldReturnError)
//            {
//                Resource.Error("Error", null)
//            } else
//            {
//                Resource.Success(listOf(Game(null, 1, "test", "Test sm", null, null, null)))
//            }
//        }
//    }
//
//    //API FAKE REPOSITORY
////    override suspend fun getGames(): Response<List<Game>>
////    {
////        return if (shouldReturnError)
////        {
////            Resource.Error("Error", null)
////        } else
////        {
////            Resource.Success(listOf(Game(null, 1, "test", "Test sm", null, null, null)))
////        }
////    }
////
////    override suspend fun getGameById(id: Int): Flow<Resource<Game>>
////    {
////        return flow {
////            if (shouldReturnError)
////            {
////                Resource.Error("Error", null)
////            } else
////            {
////                Resource.Success(Game(null, 1, "test", "Test sm", null, null, null))
////            }
////        }
////    }
////
////    override suspend fun searchGame(searchQuery: String): Flow<Resource<List<Game>>>
////    {
////        return flow {
////            if (shouldReturnError)
////            {
////                Resource.Error("Error", null)
////            } else
////            {
////                Resource.Success(listOf(Game(null, 1, "test", "Test sm", null, null, null)))
////            }
////        }
////    }
//}