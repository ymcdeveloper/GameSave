package br.com.ymc.gamesave.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import br.com.ymc.gamesave.db.AppDatabase
import br.com.ymc.gamesave.db.model.GameDB
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class GameDAOTest
{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao : GameDAO

    @Before
    fun setup()
    {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.gameDao()
    }

    @After
    fun tearDown()
    {
        database.close()
    }

    @Test
    fun insertGame() = runBlockingTest {
        val insertedGame = GameDB(1, "Test", "", "", "Teste", null, 50f)
        dao.insertGame(insertedGame)

        val selectedGame = dao.selectGameById(1)

        assertThat(selectedGame).isEqualTo(insertedGame)
    }

    @Test
    fun deleteGame() = runBlockingTest {
        val insertedGame = GameDB(1, "Test", "", "", "Teste", null, 50f)
        dao.insertGame(insertedGame)
        dao.deleteGame(insertedGame)

        val selectedGames = dao.selectSavedGames()

        assertThat(selectedGames).doesNotContain(insertedGame)
    }

    @Test
    fun checkGameExists() = runBlockingTest {
        val insertedGame = GameDB(1, "Test", "", "", "Teste", null, 50f)
        dao.insertGame(insertedGame)

        val exists = dao.gameExists(1)

        assertThat(exists).isTrue()
    }
}