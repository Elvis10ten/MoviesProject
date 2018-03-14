package com.mobymagic.moviesproject.moviedetail

import com.mobymagic.moviesproject.arch.BasePresenter
import com.mobymagic.moviesproject.arch.BaseView
import com.mobymagic.moviesproject.data.model.Movie

interface MovieDetailUiContract {

    interface View: BaseView {
        fun showMovieDetails(movie: Movie)
    }

    interface Presenter: BasePresenter<View> {
        fun processMovie(movie: Movie)
    }

}