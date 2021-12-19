package com.daurelio.m_ov_ies


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daurelio.m_ov_ies.api.MovieAPIInterface
import com.daurelio.m_ov_ies.api.MovieAPIResponseClass
import com.daurelio.m_ov_ies.databinding.ActivityMainBinding
import com.daurelio.m_ov_ies.helper.AppModule
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val BASE_URL: String = "https://streaming-availability.p.rapidapi.com/"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialization of API Service
        val apiService = AppModule().provideApiService(AppModule().provideHttpClient())

        //Temp values
        val country = "de"
        val service = "netflix"
        val genre = 18

        val movies = searchMovies(apiService, country, service, genre)

        movies.forEach {
            println("New Movie: ")
            println(it.genres)
        }
        binding.tvTitle.text = movies[0]!!.imdbID
    }

    private fun searchMovies(
        apiService: MovieAPIInterface,
        country: String,
        service: String,
        genre: Int
    ): MutableList<MovieClass> {

        val adapterData: MutableList<MovieClass> = ArrayList<MovieClass>()

        // The API call to Basic Search with given parameters
        val call = apiService.getSearch(
            country = country,
            service = service,
            genre = genre
        )

        //Result mapping to our interface
        //TODO: Change enqueue to execute as enqueue is for Async calls. I need to wait for the response before continuing
        //Review the documentation
        val result = call.enqueue(object : Callback<MovieAPIResponseClass> {

            override fun onFailure(call: Call<MovieAPIResponseClass>, t: Throwable) {
                binding.tvTitle.text = "Something went wrong!"
                print(t.localizedMessage)
                adapterData.clear()
            }

            override fun onResponse(
                call: Call<MovieAPIResponseClass>,
                response: Response<MovieAPIResponseClass>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Call OK", Toast.LENGTH_LONG).show()

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

        return adapterData
    }
}