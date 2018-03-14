package com.mobymagic.moviesproject.movielist

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.mobymagic.moviesproject.R
import com.mobymagic.moviesproject.data.model.Movie
import com.mobymagic.moviesproject.di.SimpleInjector
import com.mobymagic.moviesproject.moviedetail.MovieDetailActivity
import com.mobymagic.moviesproject.ui.ItemOffsetDecoration
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity :
        AppCompatActivity(),
        MovieListUiContract.View,
        MovieListAdapter.OnMovieClickListener {

    private val movieListPresenter = SimpleInjector.provideMovieListPresenter(SimpleInjector
            .provideMovieService(), SimpleInjector.provideUiProductionScheduler())
    private val movieListAdapter = SimpleInjector.provideMovieListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        // Change from Launcher theme to AppTheme
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setSupportActionBar(toolbar)

        setupSwipeRefreshLayout()
        setupRecyclerView()
        setupPresenter(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        movieListPresenter.dropView()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
        swipeRefreshLayout.setOnRefreshListener { movieListPresenter.onSwipeRefreshPerformed() }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(ItemOffsetDecoration(this,
                R.dimen.movieListGridSpacing))
        recyclerView.adapter = movieListAdapter
    }

    private fun setupPresenter(savedInstanceState: Bundle?) {
        movieListPresenter.takeView(this)
        movieListPresenter.fetchMovies()
    }

    override fun showProgressView() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgressView() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showErrorView() {
        val errorText = "An error occurred while loading movies"
        errorContainerView.visibility = View.VISIBLE

        errorMessageTextView.text = errorText
        errorRetryButton.setOnClickListener({
            movieListPresenter.onRetryButtonClicked()
        })
    }

    override fun hideErrorView() {
        errorContainerView.visibility = View.GONE
    }

    override fun addMovies(movies: List<Movie>) {
        movieListAdapter.addMovies(movies)
    }

    override fun clearMovies() {
        movieListAdapter.clearAll()
    }

    override fun openMovieDetail(movie: Movie) {
        MovieDetailActivity.start(this, movie)
    }

    override fun onMovieClicked(position: Int, movie: Movie) {
        movieListPresenter.onMovieClicked(movie)
    }

}
