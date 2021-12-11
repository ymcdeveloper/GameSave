package br.com.ymc.gamesave

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import br.com.ymc.gamesave.presentation.activities.MainActivity
import br.com.ymc.gamesave.presentation.adapter.AllGamesAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@UninstallModules(TestAppModule::class)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class AllGamesFragmentTest
{
    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

//    @get:Rule(order = 1)
//    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init()
    {
//        hiltRule.inject()
    }

    @Test
    fun test_allGamesDataVisible()
    {
        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.menu_my_games)).perform(click())

        onView(withId(R.id.my_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.menu_all_games)).perform(click())

        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))
    }

    @Test
    fun test_searchIsShowing()
    {
        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.imgSearch)).perform(click())

        onView(withId(R.id.searchView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_allGamesListClick() = runBlockingTest {
        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.rcvGames)).perform(RecyclerViewActions.actionOnItemAtPosition<AllGamesAdapter.MyViewHolder>(0, click()))

        onView(withId(R.id.game_detail_parent)).check(matches(isDisplayed()))
    }

    @Test
    fun test_searchGame()
    {
        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.imgSearch)).perform(click())

        onView(withId(R.id.searchView)).check(matches(isCompletelyDisplayed()))

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.searchView)))).perform(typeText("Wolfenstein"))

        Thread.sleep(1500)

        onView(withId(R.id.rcvGames)).perform(RecyclerViewActions.actionOnItemAtPosition<AllGamesAdapter.MyViewHolder>(0, click()))

        onView(withId(R.id.game_detail_parent)).check(matches(isDisplayed()))

//        onView(withText("Wolfenstein")).check(matches(isDisplayed()))
    }

    @Test
    fun test_addGameAndViewGameOnMyGames()
    {
        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.imgSearch)).perform(click())

        onView(withId(R.id.searchView)).check(matches(isCompletelyDisplayed()))

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.searchView)))).perform(typeText("Mario"))

        Thread.sleep(1000)

        onView(withId(R.id.rcvGames)).perform(RecyclerViewActions.actionOnItemAtPosition<AllGamesAdapter.MyViewHolder>(0, click()))

        onView(withId(R.id.game_detail_parent)).check(matches(isDisplayed()))

        Thread.sleep(500)

        onView(withId(R.id.fabAdd)).perform(click())

        onView(isRoot()).perform(ViewActions.pressBack());

        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.menu_my_games)).perform(click())

        onView(withId(R.id.my_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.imgSearch)).perform(click())

        onView(withId(R.id.searchView)).check(matches(isCompletelyDisplayed()))

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.searchView)))).perform(typeText("Mario"))

        Thread.sleep(100)

        onView(withId(R.id.rcvMyGames)).perform(RecyclerViewActions.actionOnItemAtPosition<AllGamesAdapter.MyViewHolder>(0, click()))

        onView(withId(R.id.game_detail_parent)).check(matches(isDisplayed()))
    }
}