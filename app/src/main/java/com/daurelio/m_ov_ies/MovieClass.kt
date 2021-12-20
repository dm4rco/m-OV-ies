package com.daurelio.m_ov_ies

import retrofit2.http.Url

var movieList = mutableListOf<MovieClass>()

val MOVIE_ID_EXTRA = "movieExtra"

data class MovieClass(
    val imdbID: String,
    val imdbRating: Int?,
    val originalMovieTitle: String?,
    val countriesMovieTitle: String?,
    val genres: ArrayList<Int>?,
    val countryOfOrigin: String?,
    val releaseYear: Int?,
    val runTimeInMinutes: Int?,
    val cast: ArrayList<String>?,
    val movieDescription: String?,
    val originalLanguage: String?,
    val posterURL: Url?,
    val streamingProvider: ArrayList<String>?,
    val id: Int? = movieList.size
)