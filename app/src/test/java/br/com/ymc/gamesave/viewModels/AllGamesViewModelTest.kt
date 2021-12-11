package br.com.ymc.gamesave.presentation.games

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.domain.repository.FakeRepository
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGamesUseCaseImpl
import br.com.ymc.gamesave.domain.use_case.api_use_case.SearchGameUseCaseImpl
import br.com.ymc.gamesave.presentation.games.utility.MainCoroutineRule
import br.com.ymc.gamesave.presentation.games.utility.getOrAwaitValueTest
import br.com.ymc.gamesave.utility.LiveDataTestUtil
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import br.com.ymc.gamesave.viewModels.UIState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private lateinit var viewModel: AllGamesViewModel
    private lateinit var gameRepository : FakeRepository
//    private lateinit var game : Game

    @Before
    fun setup()
    {
        MockitoAnnotations.initMocks(this)

        gameRepository = FakeRepository()
//        databaseRepository = FakeDatabaseRepository()

        viewModel = AllGamesViewModel(
            GetGamesUseCaseImpl(gameRepository),
            SearchGameUseCaseImpl(gameRepository)
        )

//        game = Game(null, 1, "Test1", null, null, null, null)
    }

    @Test
    fun testGetGamesUseCase_Success() {
        // Act
        viewModel.callGamesApi()

        val gameListValue = viewModel.gamesList.getOrAwaitValueTest()
        val stateValue = viewModel.state.getOrAwaitValueTest()

        // Assert
        assertThat(stateValue).isEqualTo(UIState.Success)
        assertThat(gameListValue).isNotEmpty()
    }

    @Test
    fun testGetGamesUseCase_Error()
    {
        gameRepository.setReturnError(true)

        // Act
        viewModel.callGamesApi()

        val gameListValue = viewModel.gamesList.getOrAwaitValueTest()
        val stateValue = viewModel.state.getOrAwaitValueTest()

        // Assert
        assertThat(stateValue).isEqualTo(UIState.Error("Test exception"))
        assertThat(gameListValue).isEmpty()
    }

    @Test
    fun searchGameTest() {
        viewModel.searchGame("test4")

        coroutineRule.advanceUntilIdle()

        assertThat(LiveDataTestUtil.getValue(viewModel.gamesList)).isNotEmpty()
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(UIState.Success)
    }
}