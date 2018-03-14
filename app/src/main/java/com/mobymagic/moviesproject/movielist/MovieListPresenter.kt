package com.mobymagic.moviesproject.movielist

import com.mobymagic.moviesproject.data.MovieService
import com.mobymagic.moviesproject.data.model.Movie
import com.mobymagic.moviesproject.data.model.MoviesResponse
import com.mobymagic.moviesproject.utils.EspressoIdlingResource
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import timber.log.Timber

class MovieListPresenter(private val movieService: MovieService,
                         private val scheduler: Scheduler) : MovieListUiContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var curPage: Int = 1
    private var movieListView: MovieListUiContract.View? = null

    override fun takeView(view: MovieListUiContract.View) {
        movieListView = view
    }

    override fun dropView() {
        movieListView = null
        compositeDisposable.clear()
    }

    override fun fetchMovies() {
        movieListView?.showProgressView()
        movieListView?.hideErrorView()

        backgroundTaskStarted()

        val disposable = movieService.getPopularMovies(curPage)
                .observeOn(scheduler)
                .subscribeWith(object : DisposableSingleObserver<MoviesResponse>() {

                    override fun onSuccess(moviesResponse: MoviesResponse) {
                        Timber.d("Movies loaded")
                        backgroundTaskCompleted()

                        movieListView?.hideProgressView()
                        movieListView?.addMovies(moviesResponse.movies)
                    }

                    override fun onError(throwable: Throwable) {
                        Timber.e(throwable, "Error loading movies")
                        backgroundTaskCompleted()

                        movieListView?.hideProgressView()
                        movieListView?.showErrorView()
                    }
                })

        compositeDisposable.add(disposable)
    }

    override fun onSortByDateButtonClicked() {
        // TODO
    }

    override fun onScrollToEndOfList() {
        // TODO
    }

    override fun onMovieClicked(movie: Movie) {
        movieListView?.openMovieDetail(movie)
    }

    override fun onRetryButtonClicked() {
        refreshView()
    }

    override fun onSwipeRefreshPerformed() {
        refreshView()
    }

    private fun refreshView() {
        movieListView?.clearMovies()
        curPage = 1
        fetchMovies()
    }

    private fun backgroundTaskStarted() {
        EspressoIdlingResource.increment()
    }

    private fun backgroundTaskCompleted() {
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
            EspressoIdlingResource.decrement()
        }
    }

}