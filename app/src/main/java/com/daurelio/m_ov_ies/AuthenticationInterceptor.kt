package com.daurelio.m_ov_ies

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
            .addHeader("x-rapidapi-host", "streaming-availability.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "a3a7e47ec0msh284199fe1174f92p13ebb8jsn29f9ccf452cb")
            .build()

        return chain.proceed(newRequest)
    }
}