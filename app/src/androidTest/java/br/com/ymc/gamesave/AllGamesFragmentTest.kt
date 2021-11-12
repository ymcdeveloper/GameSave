package br.com.ymc.gamesave

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.ymc.gamesave.di.TestAppModule
import br.com.ymc.gamesave.ui.activities.MainActivity
import br.com.ymc.gamesave.ui.adapter.AllGamesAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@UninstallModules(TestAppModule::class)
@ExperimentalCoroutinesApi
@HiltAndroidTest
class AllGamesFragmentTest
{
    @get:Rule(order = 0)
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init()
    {
        hiltRule.inject()
    }

    @Test
    fun test_allGamesDataVisible()
    {
        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.menu_my_games)).perform(click())

        onView(withId(R.id.my_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.menu_info)).perform(click())

        onView(withId(R.id.info_parent)).check(matches(isDisplayed()))
    }

    @Test
    fun test_searchIsShowing()
    {
        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.imgSearch)).perform(click())

        onView(withId(R.id.searchView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_allGamesListClick()
    {
        onView(withId(R.id.all_games_parent)).check(matches(isDisplayed()))

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

        onView(withId(R.id.rcvGames)).perform(RecyclerViewActions.actionOnItemAtPosition<AllGamesAdapter.MyViewHolder>(0, click()))

        onView(withId(R.id.game_detail_parent)).check(matches(isDisplayed()))

//        onView(withText("Wolfenstein")).check(matches(isDisplayed()))
    }
}