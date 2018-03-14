package com.mobymagic.moviesproject.data

import com.mobymagic.moviesproject.data.model.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<MoviesResponse>

}