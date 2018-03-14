package com.mobymagic.moviesproject.moviedetail

import com.mobymagic.moviesproject.data.model.Movie

// For this simple use, a presenter is mostly not required.
class MovieDetailPresenter : MovieDetailUiContract.Presenter {

    private var movieDetailView: MovieDetailUiContract.View? = null

    override fun takeView(view: MovieDetailUiContract.View) {
        movieDetailView = view
    }

    override fun dropView() {
        movieDetailView = null
    }

    override fun processMovie(movie: Movie) {
        movieDetailView?.showMovieDetails(movie)
    }

}