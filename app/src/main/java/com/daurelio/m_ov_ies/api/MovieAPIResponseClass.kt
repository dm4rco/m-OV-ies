/*
Author: Marco D'Aurelio
Purpose: Response Class that describes how the returned JSON is designed.
This class makes it possible to use the returned JSON.
*/

package com.daurelio.m_ov_ies.api

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
