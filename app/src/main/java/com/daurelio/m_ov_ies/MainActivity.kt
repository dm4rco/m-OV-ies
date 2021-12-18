package com.daurelio.m_ov_ies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.daurelio.m_ov_ies.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query


const val BASE_URL: String = "https://streaming-availability.p.rapidapi.com/"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRetrofitTemp()
        //TODO: Datanbinding

    }


    private fun setupRetrofitTemp() {
        val builder = OkHttpClient.Builder()

        builder.interceptors().add(AuthenticationInterceptor())
        val client = builder.build()

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(MovieAPIInterface::class.java)

        val adapterData: MutableList<MovieClass> = ArrayList<MovieClass>()

        val country = "de"
        val service = "netflix"
        val genre = 18

        val call = api.getSearch(
            country = country,
            service = service,
            genre = genre
        )
        val result = call.enqueue(object: Callback<MovieClass>())


    }

}