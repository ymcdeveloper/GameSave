package br.com.ymc.gamesave.ui.games

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCaseImpl
import br.com.ymc.gamesave.domain.use_case.api_use_case.SearchGameUseCaseImpl
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.ui.games.Utility.MainCoroutineRule
import br.com.ymc.gamesave.util.Resource
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import br.com.ymc.gamesave.viewModels.UIState
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchGameUseCaseTest
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

    @ExperimentalCoroutinesApi
    @Test
    fun testSearchGameUseCase_Success() = coroutineRule.runBlockingTest { // We need to set the coroutineRule.runBlockingTest to test functions with delay
        // Arrange
        val games = listOf(
            Game(null, 12, "test1", "test Summary", 100f, null, null),
            Game(null, 12, "sas", "test Summary", 100f, null, null),
            Game(null, 12, "ueba", "test Summary", 100f, null, null),
            Game(null, 12, "dan", "test Summary", 100f, null, null)
        )
        val mockSuccess = MockGameRepository(Resource.Success(games))

        val getGamesUseCase = GetGamesUseCaseImpl(mockSuccess)
        val getSearchGame = SearchGameUseCaseImpl(mockSuccess)
        viewModel = AllGamesViewModel(getGamesUseCase, getSearchGame)
        viewModel.gamesList.observeForever(_gamesListObserver)
        viewModel.state.observeForever(_stateObserver)

        val searchQuery = "test"


        // Act
        viewModel.searchGame(searchQuery)
        delay(500)

        // Assert
        MatcherAssert.assertThat(games, hasItems(games.find { it.name.contains(searchQuery) }))
        verify(_gamesListObserver).onChanged(games)
        verify(_stateObserver).onChanged(UIState.Success)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSearchGameUseCase_Error() = coroutineRule.runBlockingTest {

        // Arrange
        val mockError = MockGameRepository(Resource.Error("Error to load games"))

        val getGamesUseCase = GetGamesUseCaseImpl(mockError)
        val getSearchGame = SearchGameUseCaseImpl(mockError)
        viewModel = AllGamesViewModel(getGamesUseCase, getSearchGame)
        viewModel.gamesList.observeForever(_gamesListObserver)
        viewModel.state.observeForever(_stateObserver)

        // Act
        viewModel.searchGame("test")
        delay(500)

        // Assert
//        verify(_gamesListObserver).onChanged()
        verify(_stateObserver).onChanged(UIState.Error("Error to load games"))
    }
}