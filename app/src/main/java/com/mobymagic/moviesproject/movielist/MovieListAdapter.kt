package com.mobymagic.moviesproject.movielist

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mobymagic.moviesproject.R
import com.mobymagic.moviesproject.data.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*
import java.util.*
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class MovieListAdapter(private val clickListener : MovieListAdapter.OnMovieClickListener) :
        RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private val movies = ArrayList<Movie>()

    fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
        notifyItemRangeInserted(0, movies.size - 1)
    }

    fun clearAll() {
        movies.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, clickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie, clickListener: MovieListAdapter.OnMovieClickListener) {
            val context = itemView.context
            itemView.setBackgroundColor(Color.DKGRAY)

            Picasso.get().load(movie.getPosterUrl()).into(itemView.movieImageView)
            itemView.movieNameTextView.text = movie.title

            val voteAverage = String.format(Locale.US, "%.2f", movie.voteAverage)
            itemView.movieVoteTextView.text = context.getString(R.string.movieListAverageVote,
                    voteAverage)

            itemView.setOnClickListener { clickListener.onMovieClicked(adapterPosition, movie) }
        }

    }

    interface OnMovieClickListener {
        fun onMovieClicked(position: Int, movie: Movie)
    }

}