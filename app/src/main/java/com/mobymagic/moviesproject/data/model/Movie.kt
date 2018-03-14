package com.mobymagic.moviesproject.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

private const val IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w780"

data class Movie(val id: Int,
                 val title: String,
                 val overview: String,
                 val popularity: Double,
                 @SerializedName("poster_path")
                 val posterPath: String,
                 @SerializedName("backdrop_path")
                 val backdropPath: String,
                 @SerializedName("release_date")
                 val releaseDate: String,
                 @SerializedName("vote_average")
                 val voteAverage: Double,
                 @field:SerializedName("vote_count")
                 val voteCount: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt())

    fun getPosterUrl(): String {
        return IMAGE_URL_PREFIX + posterPath
    }

    fun getBackdropUrl(): String {
        return IMAGE_URL_PREFIX + backdropPath
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeDouble(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeString(releaseDate)
        parcel.writeDouble(voteAverage)
        parcel.writeInt(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}