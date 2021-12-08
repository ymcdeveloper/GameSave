package br.com.ymc.gamesave.presentation.games

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import br.com.ymc.gamesave.core.util.Resource
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.data.remote.dto.toGameDB
import br.com.ymc.gamesave.domain.FakeDatabaseRepository
import br.com.ymc.gamesave.domain.repository.FakeRepository
import br.com.ymc.gamesave.domain.use_case.api_use_case.GetGameDetailUseCaseImpl
import br.com.ymc.gamesave.domain.use_case.db_use_case.*
import br.com.ymc.gamesave.presentation.games.utility.MainCoroutineRule
import br.com.ymc.gamesave.utility.LiveDataTestUtil
import br.com.ymc.gamesave.viewModels.GameDetailViewModel
import br.com.ymc.gamesave.viewModels.UIState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GameDetailViewModelTest
{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: GameDetailViewModel

    private lateinit var game : Game

    private lateinit var gameRepository : FakeRepository
    private lateinit var databaseRepository : FakeDatabaseRepository

    @Before
    fun setupViewModel()
    {
        MockitoAnnotations.initMocks(this)

        gameRepository = FakeRepository()
        databaseRepository = FakeDatabaseRepository()

        viewModel = GameDetailViewModel(
            GetGameDetailUseCaseImpl(gameRepository),
            GetDBGameDetailUseCaseImpl(databaseRepository),
            DeleteGameUseCaseImpl(databaseRepository),
            SaveGameUseCaseImpl(databaseRepository),
            CheckGameExistsUseCaseImpl(databaseRepository)
        )

        game = Game(null, 1, "Test1", null, null, null, null)
    }

    @Test
    fun loadGame_dataLoaded() {
        val id = 1

        // Act
        viewModel.getGame(id)

        // Assert
        assertThat(LiveDataTestUtil.getValue(viewModel.game).id).isEqualTo(id)
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(UIState.Success)
    }

    @Test
    fun loadGame_errorToLoad() {
        gameRepository.setReturnError(true)

        // Act
        viewModel.getGame(1)

        // Assert
        assertThat(LiveDataTestUtil.getValue(viewModel.game)).isNull()
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(UIState.Error("Test exception"))
    }

    @Test
    fun loadGameFromDB_dataLoaded() {
        val id = 1

        databaseRepository.addGame(game.toGameDB())

        // Act
        viewModel.getGameFromDB(id)

        // Assert
        assertThat(LiveDataTestUtil.getValue(viewModel.game).id).isEqualTo(id)
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(UIState.Success)
    }

    @Test
    fun loadGameFromDB_errorToLoad() {
        databaseRepository.setReturnError(true)

        databaseRepository.addGame(game.toGameDB())

        // Act
        viewModel.getGameFromDB(game.id)

        // Assert
        assertThat(LiveDataTestUtil.getValue(viewModel.game)).isNull()
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(UIState.Error("Error"))
    }

    @Test
    fun insertGameToDB_success() {
        viewModel.insertGameToDB(game)

        viewModel.getGameFromDB(game.id)

        // Assert
        assertThat(LiveDataTestUtil.getValue(viewModel.game).id).isEqualTo(game.id)
        assertThat(LiveDataTestUtil.getValue(viewModel.state)).isEqualTo(UIState.Success)
    }

    @Test
    fun deleteGameFromDB() {
        databaseRepository.setReturnError(true)

        viewModel.insertGameToDB(game)

        assertThat(databaseRepository.games).contains(game.toGameDB())

        viewModel.deleteGame(game)

        assertThat(databaseRepository.games).doesNotContain(game.toGameDB())

        // Assert
        assertThat(LiveDataTestUtil.getValue(viewModel.isGameDeleted)).isTrue()
    }
}
