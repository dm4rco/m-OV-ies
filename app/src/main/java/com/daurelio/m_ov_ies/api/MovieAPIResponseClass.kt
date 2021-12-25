package com.daurelio.m_ov_ies.api

import retrofit2.http.Url

data class MovieAPIResponseClass(
    val results: List<Movie>,
    val totalPages: Int,

    ) {
    data class Movie(
        val imdbID: String,
        val imdbRating: Int?,
        val title: String,
        val originalTitle: String?,
        val genres: ArrayList<Int>,
        val countries: ArrayList<String>,
        val year: Int,
        val runtime: Long,
        val cast: ArrayList<String>,
        val overview: String?,
        val originalLanguage: String,
        val posterPath: String,
        val streamingInfo: HashMap<String, Service> //streaming info is another array inside the JSONs
    )

    data class Service(
        val name: String
    )
}
