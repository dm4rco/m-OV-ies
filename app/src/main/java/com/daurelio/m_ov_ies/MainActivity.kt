package com.daurelio.m_ov_ies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.daurelio.m_ov_ies.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL: String = "https://streaming-availability.p.rapidapi.com/"

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentData()

        //TODO: Datanbinding

    }

    private fun getCurrentData() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieServiceInterface::class.java)


        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getSearch().awaitResponse()
            if(response.isSuccessful) {
                val data = response.body()!!
                Log.d(TAG, data.toString())

                withContext(Dispatchers.Main) {
                    binding.tvTitle.text = data.toString()
                }
            }
        }
    }

}