package br.com.ymc.gamesave.ui.games

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.ymc.gamesave.domain.repository.GameRepository
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCase
import br.com.ymc.gamesave.domain.use_case.api_use_case.SearchGameUseCase
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.ui.games.Utility.MainCoroutineRule
import br.com.ymc.gamesave.util.Resource
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import br.com.ymc.gamesave.viewModels.UIState
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AllGamesViewModelTest
{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var _gamesListObserver : Observer<List<Game>>
    @Mock
    private lateinit var _stateObserver : Observer<UIState>

    private lateinit var viewModel: AllGamesViewModel

    @Before
    fun setup()
    {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetGamesUseCase_Success()
    {
        // Arrange
        val games = listOf(
            Game(null, 12, "test1", "test Summary", 100f, null, null)
        )
        val mockSuccess = MockGameRepository(Resource.Success(games))

        val getGamesUseCase = GetGamesUseCase(mockSuccess)
        val getSearchGame = SearchGameUseCase(mockSuccess)
        viewModel = AllGamesViewModel(getGamesUseCase, getSearchGame)
        viewModel.gamesList.observeForever(_gamesListObserver)
        viewModel.state.observeForever(_stateObserver)

        // Act
        viewModel.callGamesApi()

        // Assert
        verify(_gamesListObserver).onChanged(games)
        verify(_stateObserver).onChanged(UIState.Success)
    }

    @Test
    fun testGetGamesUseCase_Error()
    {
        // Arrange
        val mockError = MockGameRepository(Resource.Error("Error to load games"))

        val getGamesUseCase = GetGamesUseCase(mockError)
        val getSearchGame = SearchGameUseCase(mockError)
        viewModel = AllGamesViewModel(getGamesUseCase, getSearchGame)
        viewModel.gamesList.observeForever(_gamesListObserver)
        viewModel.state.observeForever(_stateObserver)

        // Act
        viewModel.callGamesApi()

        // Assert
//        verify(_gamesListObserver).onChanged()
        verify(_stateObserver).onChanged(UIState.Error("Error to load games"))
    }
}

class MockGameRepository(private val result: Resource<List<Game>>) : GameRepository
{
    override suspend fun getGames(): Flow<Resource<List<Game>>>
    {
        return flow {
            emit(result)
        }
    }

    override suspend fun getGameById(id: Int): Flow<Resource<Game>>
    {
        TODO("Not yet implemented")
    }

    override suspend fun searchGame(searchQuery: String): Flow<Resource<List<Game>>>
    {
        return flow {
            emit(result)
        }
    }
}