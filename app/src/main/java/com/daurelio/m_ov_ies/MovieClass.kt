package com.daurelio.m_ov_ies

const val MOVIE_ID = "movieExtra"

data class MovieClass(
    val imdbID: String,
    val imdbRating: Int?,
    val originalMovieTitle: String?,
    val countriesMovieTitle: String?,
    val genres: ArrayList<Int>?,
    val countryOfOrigin: ArrayList<String>?,
    val releaseYear: Int?,
    val runTimeInMinutes: Long?,
    val cast: ArrayList<String>?,
    val movieDescription: String?,
    val originalLanguage: String?,
    val posterURL: String?,
    val streamingProvider: ArrayList<String>?,
    val id: Int? = 0
)