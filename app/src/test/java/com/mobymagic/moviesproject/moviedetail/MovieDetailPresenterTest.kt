package com.mobymagic.moviesproject.moviedetail

import com.mobymagic.moviesproject.data.model.Movie
import com.mobymagic.moviesproject.di.SimpleInjector
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieDetailPresenterTest {

    private val movie = Movie(1, "title", "overview", 5.0,"posterPath",
            "backdropPath", "releaseData", 2.0, 10)

    private val movieDetailView: MovieDetailUiContract.View = mock()
    private lateinit var movieDetailPresenter: MovieDetailUiContract.Presenter

    @Before
    fun setUpPresenter() {
        movieDetailPresenter = SimpleInjector.provideMovieDetailPresenter()
        movieDetailPresenter.takeView(movieDetailView)
    }

    @After
    fun releasePresenter() {
        movieDetailPresenter.dropView()
    }

    @Test
    fun processMovie_shouldShowMovieDetail() {
        movieDetailPresenter.processMovie(movie)

        verify(movieDetailView).showMovieDetails(movie)
    }

    @Test
    fun dropView_shouldNotInteractWithView() {
        movieDetailPresenter.dropView()

        verifyZeroInteractions(movieDetailView)
    }

}