package com.mobymagic.moviesproject.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(@SerializedName("page")
                          val pageNum: Int,
                          @SerializedName("results")
                          val movies: List<Movie>,
                          @SerializedName("total_results")
                          val totalResults: Int,
                          @SerializedName("total_pages")
                          val totalPages: Int)