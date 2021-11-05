package br.com.ymc.gamesave

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.ymc.gamesave.adapter.AllGamesAdapter
import br.com.ymc.gamesave.ui.activities.MainActivity
import br.com.ymc.gamesave.util.EspressoIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AllGamesFragmentTest
{
    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource()
    {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource()
    {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
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