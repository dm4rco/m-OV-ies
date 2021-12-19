package com.daurelio.m_ov_ies


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daurelio.m_ov_ies.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
        val result = call.enqueue(object: Callback<MovieAPIResponseClass> {
            override fun onFailure(call: Call<MovieAPIResponseClass>, t: Throwable) {
                binding.tvTitle.text = "Something went wrong!"
                print(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<MovieAPIResponseClass>,
                response: Response<MovieAPIResponseClass>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Call OK", Toast.LENGTH_LONG).show()

                    //TODO: Delete this
                    println("Response is Successful!?")
                    println(response.message())
                    println(response.code())
                    println(response.raw())
                    println(response.body())

                    val searchResponse: MovieAPIResponseClass? = response.body()
                    searchResponse!!.results.forEach {
                        val movie = MovieClass(
                            imdbID = it.imdbID,
                            imdbRating = it.imdbRating,
                            originalMovieTitle = it.originalMovieTitle,
                            countriesMovieTitle = it.countriesMovieTitle,
                            genres = it.genres,
                            countryOfOrigin = it.countryOfOrigin,
                            releaseYear = it.releaseYear,
                            runTimeInMinutes = it.runTimeInMinutes,
                            cast = it.cast,
                            movieDescription = it.movieDescription,
                            originalLanguage = it.originalLanguage,
                            posterURL = it.posterURL,
                            streamingProvider = it.streamingProvider
                        )
                        adapterData.add(movie)
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Something went wrong: " + response.code(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}