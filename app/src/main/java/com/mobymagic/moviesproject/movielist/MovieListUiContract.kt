package com.mobymagic.moviesproject.movielist

import com.mobymagic.moviesproject.arch.BasePresenter
import com.mobymagic.moviesproject.arch.BaseView
import com.mobymagic.moviesproject.data.model.Movie

interface MovieListUiContract {

    interface View: BaseView {

        fun showProgressView()
        fun hideProgressView()

        fun showErrorView()
        fun hideErrorView()

        fun addMovies(movies: List<Movie>)
        fun clearMovies()
        fun openMovieDetail(movie: Movie)

    }

    interface Presenter: BasePresenter<View> {

        fun fetchMovies()
        fun onSortByDateButtonClicked()
        fun onRetryButtonClicked()
        fun onSwipeRefreshPerformed()
        fun onScrollToEndOfList()
        fun onMovieClicked(movie: Movie)

    }

}