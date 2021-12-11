package br.com.ymc.gamesave.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.data.remote.dto.toGameDB
import br.com.ymc.gamesave.domain.FakeDatabaseRepository
import br.com.ymc.gamesave.domain.use_case.db_use_case.FilterSavedGamesUseCaseImpl
import br.com.ymc.gamesave.domain.use_case.db_use_case.GetCountUseCaseImpl
import br.com.ymc.gamesave.domain.use_case.db_use_case.GetSavedGamesUseCaseImpl
import br.com.ymc.gamesave.presentation.games.utility.MainCoroutineRule
import br.com.ymc.gamesave.utility.LiveDataTestUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MyGamesViewModelTest
{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MyGamesViewModel
    private lateinit var databaseRepository: FakeDatabaseRepository

    @Before
    fun setup()
    {
        databaseRepository = FakeDatabaseRepository()

        viewModel = MyGamesViewModel(
            GetSavedGamesUseCaseImpl(databaseRepository),
            FilterSavedGamesUseCaseImpl(),
            GetCountUseCaseImpl(databaseRepository)
        )
    }

    @Test
    fun loadGames_Success()
    {
        databaseRepository.addGame(Game(null, 1, "Test1", null, null, null, null).toGameDB())

        //Act
        viewModel.loadGames()

        // Assert
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(UIState.Success)
        assertThat(LiveDataTestUtil.getValue(viewModel.gamesList)).isNotEmpty()
    }

    @Test
    fun loadGames_Error()
    {
        databaseRepository.setReturnError(true)
        databaseRepository.addGame(Game(null, 1, "Test1", null, null, null, null).toGameDB())

        //Act
        viewModel.loadGames()

        // Assert
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(UIState.Error("Error"))
        assertThat(LiveDataTestUtil.getValue(viewModel.gamesList)).isNull()
    }

    @Test
    fun searchGames_Success()
    {
        databaseRepository.addGame(Game(null, 1, "Test1", null, null, null, null).toGameDB())
        databaseRepository.addGame(Game(null, 2, "Test2", null, null, null, null).toGameDB())
        databaseRepository.addGame(Game(null, 3, "Test3", null, null, null, null).toGameDB())

        viewModel.loadGames()
        //Act
        viewModel.searchGame("Test2")

        // Assert
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(UIState.Success)
        assertThat(LiveDataTestUtil.getValue(viewModel.gamesList)).isNotEmpty()
    }
}