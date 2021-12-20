package com.daurelio.m_ov_ies


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.daurelio.m_ov_ies.api.MovieAPIInterface
import com.daurelio.m_ov_ies.api.MovieAPIResponseClass
import com.daurelio.m_ov_ies.databinding.ActivityMainBinding
import com.daurelio.m_ov_ies.helper.AppModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val BASE_URL: String = "https://streaming-availability.p.rapidapi.com/"
val adapterData: MutableList<MovieClass> = ArrayList<MovieClass>()

class MainActivity : AppCompatActivity(), MovieClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialization of API Service
        val apiService = AppModule().provideApiService(AppModule().provideHttpClient())

        mainActivity = this

        //Temp values
        val country = "de"
        val service = "netflix"
        val genre = 18

        searchMovies(apiService, country, service, genre)
    }

    private fun searchMovies(
        apiService: MovieAPIInterface,
        country: String,
        service: String,
        genre: Int
    ) {

        // The API call to Basic Search with given parameters
        val call = apiService.getSearch(
            country = country,
            service = service,
            genre = genre
        )

        //Result mapping to our interface
        val result = call.enqueue(object : Callback<MovieAPIResponseClass> {

            override fun onFailure(call: Call<MovieAPIResponseClass>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
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
                    updateMovieOnUI()
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

    //Fill recycler view with movie cards
    private fun updateMovieOnUI() {
        adapterData.forEach {
            println("New Movie: ")
            println(it.genres)
        }
        //binding.tvTitle.text = adapterData.size.toString()

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)
            adapter = CardAdapter(adapterData, mainActivity)
        }

    }

    override fun onClick(movie: MovieClass) {
        val intent = Intent(applicationContext, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID_EXTRA, movie.id)
        startActivity(intent)
    }

}