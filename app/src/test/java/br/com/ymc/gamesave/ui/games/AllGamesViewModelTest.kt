package br.com.ymc.gamesave.ui.games

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCase
import br.com.ymc.gamesave.domain.use_case.api_use_case.SearchGameUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.ui.games.utility.MainCoroutineRule
import br.com.ymc.gamesave.ui.games.utility.getOrAwaitValueTest
import br.com.ymc.gamesave.util.Resource
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import br.com.ymc.gamesave.viewModels.UIState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AllGamesViewModelTest
{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

//    @Mock
//    private lateinit var _gamesListObserver : Observer<List<Game>>
//
//    @Mock
//    private lateinit var _stateObserver : Observer<UIState>

    private lateinit var viewModel: AllGamesViewModel

    @Before
    fun setup()
    {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetGamesUseCase_Success() {
        // Arrange
        val games = listOf(
            Game(null, 12, "test1", "test Summary", 100f, null, "")
        )
        val getGamesUseCase = MockGetGamesUseCase(Resource.Success(games))
        val searchGameUseCase = MockSearchGamesUseCase(Resource.Success(games))

        viewModel = AllGamesViewModel(getGamesUseCase, searchGameUseCase)

        // Act
        viewModel.callGamesApi()

        val gameListValue = viewModel.gamesList.getOrAwaitValueTest()
        val stateValue = viewModel.state.getOrAwaitValueTest()

        // Assert
        assertThat(stateValue).isEqualTo(UIState.Success)
        assertThat(gameListValue).isEqualTo(games)
    }

    @Test
    fun testGetGamesUseCase_Error()
    {
        // Arrange
        val mockError = MockGetGamesUseCase(Resource.Error("Error"))
        val mockErrorSearch = MockSearchGamesUseCase(Resource.Error("Error to load games"))

        viewModel = AllGamesViewModel(mockError, mockErrorSearch)

        // Act
        viewModel.callGamesApi()

        val gameListValue = viewModel.gamesList.getOrAwaitValueTest()
        val stateValue = viewModel.state.getOrAwaitValueTest()

        // Assert
        assertThat(stateValue).isEqualTo(UIState.Error("Error"))
        assertThat(gameListValue).isEmpty()
    }

    @Test
    fun searchGameTest() = coroutineRule.runBlockingTest {
        // Arrange
        val games = listOf(
            Game(null, 1, "test1", "test Summary", 100f, null, ""),
            Game(null, 12, "daniel", "test Summary", 100f, null, "")
        )
        val getGamesUseCase = MockGetGamesUseCase(Resource.Success(games))
        val searchGameUseCase = MockSearchGamesUseCase(Resource.Success(games))

        viewModel = AllGamesViewModel(getGamesUseCase, searchGameUseCase)

        viewModel.searchGame("daniel")

        advanceUntilIdle()

        val gameListValue = viewModel.gamesList.getOrAwaitValueTest()
        val stateValue = viewModel.state.getOrAwaitValueTest()

        assertThat(gameListValue).contains(games[1])
        assertThat(stateValue).isEqualTo(UIState.Success)
    }
}

class MockGetGamesUseCase(private val result: Resource<List<Game>>) : GetGamesUseCase
{
    override suspend fun invoke(): Flow<Resource<List<Game>>>
    {
        return flow {
            emit(result)
        }
    }
}

class MockSearchGamesUseCase(private val result: Resource<List<Game>>) : SearchGameUseCase
{
    override suspend fun invoke(searchQuery: String): Flow<Resource<List<Game>>>
    {
        return flow {
            val newSearchQuery = searchQuery.replace("'", "")
//            emit(Resource.Loading())
            val data = result.data?.filter {
                it.name == newSearchQuery
            }

            emit(Resource.Success(data!!))
        }
    }
}

//class MockGameRepository(private val result: Resource<List<Game>>) : GameRepository
//{
//    override suspend fun getGames(): Flow<Resource<List<Game>>>
//    {
//        return flow {
//            emit(result)
//        }
//    }
//
//    override suspend fun getGameById(id: Int): Flow<Resource<Game>>
//    {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun searchGame(searchQuery: String): Flow<Resource<List<Game>>>
//    {
//        return flow {
//            emit(result)
//        }
//    }
//}