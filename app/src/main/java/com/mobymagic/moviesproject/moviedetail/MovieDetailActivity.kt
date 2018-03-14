package com.mobymagic.moviesproject.moviedetail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mobymagic.moviesproject.R
import com.mobymagic.moviesproject.data.model.Movie
import com.mobymagic.moviesproject.di.SimpleInjector
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailUiContract.View {

    private val htmlUtils = SimpleInjector.provideHtmlUtils()
    private val movieDetailPresenter = SimpleInjector.provideMovieDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setupPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        movieDetailPresenter.dropView()
    }

    private fun setupPresenter() {
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        movieDetailPresenter.takeView(this)
        movieDetailPresenter.processMovie(movie)
    }

    override fun showMovieDetails(movie: Movie) {
        Picasso.get().load(movie.getBackdropUrl()).into(movieImageView)

        val detailsText = getString(R.string.movieDetailTextStructure, movie.title,
                movie.releaseDate, movie.overview)
        movieDetailTextView.text = htmlUtils.fromHtml(detailsText)
    }

    companion object {

        private const val EXTRA_MOVIE = "EXTRA_MOVIE"

        fun start(context: Context, movie: Movie) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            context.startActivity(intent)
        }

    }

}
