//package br.com.ymc.gamesave.db.dao
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.filters.SmallTest
//import br.com.ymc.gamesave.data.local.dao.GameDAO
//import br.com.ymc.gamesave.data.local.AppDatabase
//import br.com.ymc.gamesave.data.local.entity.GameDB
//import com.google.common.truth.Truth.assertThat
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import javax.inject.Inject
//import javax.inject.Named
//
//@ExperimentalCoroutinesApi
//@SmallTest
//@HiltAndroidTest
//class GameDAOTest
//{
//    @get:Rule(order = 0)
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule(order = 1)
//    var hiltRule = HiltAndroidRule(this)
//
//    @Inject
//    @Named("test_db")
//    lateinit var database: AppDatabase
//    private lateinit var dao : GameDAO
//
//    @Before
//    fun setup()
//    {
//        hiltRule.inject()
//        dao = database.gameDao()
//    }
//
//    @After
//    fun tearDown()
//    {
//        database.close()
//    }
//
//    @Test
//    fun insertGameTest() = runBlockingTest {
//        val insertedGame = GameDB(1, "Test", "", "", "Teste", null, 50f)
//        dao.insertGame(insertedGame)
//
//        val selectedGame = dao.selectGameById(1)
//
//        assertThat(selectedGame).isEqualTo(insertedGame)
//    }
//
//    @Test
//    fun deleteGameTest() = runBlockingTest {
//        val insertedGame = GameDB(1, "Test", "", "", "Teste", null, 50f)
//        dao.insertGame(insertedGame)
//        dao.deleteGame(insertedGame)
//
//        val selectedGames = dao.selectSavedGames()
//
//        assertThat(selectedGames).doesNotContain(insertedGame)
//    }
//
//    @Test
//    fun checkGameExistsTest() = runBlockingTest {
//        val insertedGame = GameDB(1, "Test", "", "", "Teste", null, 50f)
//        dao.insertGame(insertedGame)
//
//        val exists = dao.gameExists(1)
//
//        assertThat(exists).isTrue()
//    }
//
//    @Test
//    fun checkGamesCount() = runBlockingTest {
//        val insertedGames = listOf(
//            GameDB(1, "Test", "", "", "Teste", null, 50f),
//            GameDB(2, "Test2", "", "", "Teste2", null, 100f)
//        )
//
//        for(game in insertedGames)
//        {
//            dao.insertGame(game)
//        }
//
//        val count = dao.getCount()
//
//        assertThat(count).isEqualTo(2)
//    }
//
////    @Test
////    fun testLaunchFragmentInHiltContainer()
////    {
////        launchFragmentInHiltContainer<AllGamesFragment> {
////
////        }
////    }
//}