package com.mobymagic.moviesproject.movielist

import com.mobymagic.moviesproject.data.MovieService
import com.mobymagic.moviesproject.data.model.Movie
import com.mobymagic.moviesproject.data.model.MoviesResponse
import com.mobymagic.moviesproject.di.SimpleInjector
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.verifyZeroInteractions
import java.io.IOException

class MovieListPresenterTest {

    private val movie = Movie(1, "title", "overview", 5.0,"posterPath",
            "backdropPath", "releaseData", 2.0, 10)
    private val movieResponse = MoviesResponse(1, listOf(movie), 100, 5)

    private val movieService: MovieService = mock()
    private val movieListView: MovieListUiContract.View = mock()

    private lateinit var movieListPresenter: MovieListUiContract.Presenter

    @Before
    fun setUpPresenter() {
        movieListPresenter = SimpleInjector.provideMovieListPresenter(movieService,
                SimpleInjector.provideUiTestScheduler())
        movieListPresenter.takeView(movieListView)
    }

    @After
    fun releasePresenter() {
        movieListPresenter.dropView()
    }

    @Test
    fun onFetchMovies_shouldShowErrorView_whenHttpRequestFails() {
        whenever(movieService.getPopularMovies(anyInt())).thenReturn(Single.error(IOException()))

        movieListPresenter.fetchMovies()

        verify(movieListView).hideErrorView()
        verify(movieListView).showProgressView()

        verify(movieListView).hideProgressView()
        verify(movieListView).showErrorView()
    }

    @Test
    fun onLoadPopularMovies_shouldAddMovies_whenMoviesIsSuccessfullyLoaded() {
        whenever(movieService.getPopularMovies(anyInt())).thenReturn(Single.just(movieResponse))

        movieListPresenter.fetchMovies()

        verify(movieListView).hideErrorView()
        verify(movieListView).showProgressView()

        verify(movieListView).hideProgressView()
        verify(movieListView).addMovies(movieResponse.movies)
    }

    @Test
    fun onMovieClicked_shouldOpenMovieDetail() {
        val movie = movieResponse.movies[0]
        movieListPresenter.onMovieClicked(movie)

        verify(movieListView).openMovieDetail(movie)

        verifyZeroInteractions(movieService)
    }

    @Test
    fun onRetryButtonClicked_shouldReloadMovies() {
        whenever(movieService.getPopularMovies(anyInt())).thenReturn(Single.just(movieResponse))
        movieListPresenter.onRetryButtonClicked()

        verify(movieListView).clearMovies()
        verify(movieListView).hideErrorView()
        verify(movieListView).showProgressView()
    }

    @Test
    fun onSwipeRefreshPerformed_shouldReloadMovies() {
        whenever(movieService.getPopularMovies(anyInt())).thenReturn(Single.just(movieResponse))
        movieListPresenter.onRetryButtonClicked()

        verify(movieListView).clearMovies()
        verify(movieListView).hideErrorView()
        verify(movieListView).showProgressView()
    }

    @Test
    fun onSortByDateButtonClicked() {
        movieListPresenter.onSortByDateButtonClicked(movieResponse.movies)

        verify(movieListView).clearMovies()
        verify(movieListView).addMovies(movieResponse.movies)
    }

    @Test
    fun onScrollToEndOfList() {
    }

    @Test
    fun dropView_shouldNotInteractWithView() {
        movieListPresenter.dropView()

        verifyZeroInteractions(movieListView)
    }

}