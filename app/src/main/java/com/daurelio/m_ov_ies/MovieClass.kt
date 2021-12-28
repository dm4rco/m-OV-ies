package com.daurelio.m_ov_ies

import com.daurelio.m_ov_ies.api.MovieAPIResponseClass

const val MOVIE_ID = "movieExtra"

data class MovieClass(
    val imdbID: String,
    val imdbRating: Int?,
    val originalMovieTitle: String?,
    val countriesMovieTitle: String?,
    val countryOfOrigin: ArrayList<String>?,
    val releaseYear: Int?,
    val runTimeInMinutes: Long?,
    val cast: ArrayList<String>?,
    val movieDescription: String?,
    val originalLanguage: String?,
    val posterURL: String?,
    val streamingProvider: HashMap<String, MovieAPIResponseClass.Service>,
    val id: String
)