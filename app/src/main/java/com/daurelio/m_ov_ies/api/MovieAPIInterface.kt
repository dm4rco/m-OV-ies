/*
Author: Marco D'Aurelio
Purpose: API Interface that describes which API Calls are available and which information
is needed in order to call it.
Only one API Call is available: Basic Search
*/

package com.daurelio.m_ov_ies.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIInterface {

    @GET("search/basic")
    fun getSearch(
        @Query("country") country: String,
        @Query("service") service: String,
        @Query("type") type: String = "movie",
        @Query("genre") genre: Int? = null,
        @Query("keyword") keyword: String? = null,
        @Query("page") page: Int = 1,
        @Query("output_language") output_language: String = "en",
        @Query("language") language: String = "en"
    ): Call<MovieAPIResponseClass>
}