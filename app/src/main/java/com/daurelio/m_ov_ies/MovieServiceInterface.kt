package com.daurelio.m_ov_ies

import retrofit2.Call
import retrofit2.http.GET

interface MovieServiceInterface {

    @GET("search/basic")
    fun getSearch(): Call<List<MovieClass>>

    @GET("genres")
    fun getGenres(): Call<Map<Int, String>>

}