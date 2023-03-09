package com.example.flixter

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single movie from the Movie Database API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
class Movie {
    @JvmField
    @SerializedName("poster_path")
    var posterImageUrl: String? = null

    @JvmField
    @SerializedName("title")
    var title: String? = null

    @JvmField
    @SerializedName("overview")
    var overview: String? = null
}