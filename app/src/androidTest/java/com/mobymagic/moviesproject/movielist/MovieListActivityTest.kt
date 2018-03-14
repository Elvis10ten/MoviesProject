package com.mobymagic.moviesproject.movielist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.mobymagic.moviesproject.R
import com.mobymagic.moviesproject.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MovieListActivityTest {

    @JvmField
    @Rule
    var activityRule: ActivityTestRule<MovieListActivity> = ActivityTestRule(MovieListActivity::class.java)

    @Before
    fun setUp() {
        // Register BackgroundWork IdlingResource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun onMovieClicked_shouldOpenMovieDetails() {
        onView(withId(R.id.recyclerView)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView(withId(R.id.movieDetailTextView)).check(matches(isDisplayed()))
    }

}