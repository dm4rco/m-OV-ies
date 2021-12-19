package com.daurelio.m_ov_ies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIInterface {

    //Request URL: https://streaming-availability.p.rapidapi.com/search/basic
    // ?country=us&service=netflix&type=movie&genre=18&page=1&output_language=en&language=en

    @GET("search/basic")
    fun getSearch(
        @Query("country") country: String,
        @Query("service") service: String,
        @Query("type") type: String = "movie",
        @Query("genre") genre: Int,
        @Query("page") page: Int = 1,
        @Query("output_language") output_language: String = "en",
        @Query("language") language: String = "en"
    ): Call<MovieAPIResponseClass>

    @GET("genres")
    fun getGenres(): Call<Map<Int, String>>

}