package com.daurelio.m_ov_ies.helper

import android.os.Build
import com.daurelio.m_ov_ies.BASE_URL
import com.daurelio.m_ov_ies.BuildConfig
import com.daurelio.m_ov_ies.api.AuthenticationInterceptor
import com.daurelio.m_ov_ies.api.MovieAPIInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModule() {

    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.interceptors().add(AuthenticationInterceptor())

        builder.retryOnConnectionFailure(false)
        builder.interceptors().add(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })

        return builder.build()
    }

    fun provideApiService(httpClient: OkHttpClient): MovieAPIInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build().create(MovieAPIInterface::class.java)
    }

}