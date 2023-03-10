package com.example.flixter

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

/**
 * The Model for storing a single movie from the Movie Database API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
class NowPlayingMovie(
    val movieId: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String
) {
    companion object {
        fun fromJsonArray(movieJsonArray: JSONArray): List<NowPlayingMovie> {
            val movies = mutableListOf<NowPlayingMovie>()
            for (i in 0 until movieJsonArray.length()) {
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    NowPlayingMovie(
                        movieJson.getInt("id"),
                        movieJson.getString("title"),
                        movieJson.getString("overview"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("backdrop_path")
                    )
                )
            }
            return movies
        }
    }
}