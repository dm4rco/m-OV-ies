package com.daurelio.m_ov_ies


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
const val POSTER_HOST: String = "https://image.tmdb.org/t/p/w185"
val adapterData: MutableList<MovieClass> = ArrayList<MovieClass>()

class MainActivity : AppCompatActivity(), MovieClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivity: MainActivity

    //Initialization of APIService
    private val apiService = AppModule().provideApiService(AppModule().provideHttpClient())

    //Temp values for mandatory and static fields for search
    private val country = "de"
    private val service = "netflix"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainActivity = this


        searchMovies(
            apiService = apiService,
            country = country,
            service = service
        )

        binding.btnSearch.setOnClickListener {
            executeSearch()
        }

        binding.etMovieSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                executeSearch()
            }
            false
        }
    }

    private fun searchMovies(
        apiService: MovieAPIInterface,
        country: String,
        service: String,
        genre: Int? = null,
        keyword: String? = null
    ) {

        // The API call to Basic Search with given parameters
        val call = apiService.getSearch(
            country = country,
            service = service,
            genre = genre,
            keyword = keyword
        )

        //Result mapping to our interface
        val result = call.enqueue(object : Callback<MovieAPIResponseClass> {

            override fun onFailure(call: Call<MovieAPIResponseClass>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Something went wrong. Please try again.",
                    Toast.LENGTH_LONG
                ).show()
                println("CALL FAILED!")
                print(t.localizedMessage)
                adapterData.clear()
            }

            override fun onResponse(
                call: Call<MovieAPIResponseClass>,
                response: Response<MovieAPIResponseClass>
            ) {
                if (response.isSuccessful) {
                    val searchResponse: MovieAPIResponseClass? = response.body()

                    searchResponse!!.results.forEachIndexed { index, it ->
                        val movie = MovieClass(
                            id = index,
                            imdbID = it.imdbID,
                            imdbRating = it.imdbRating,
                            originalMovieTitle = it.title,
                            countriesMovieTitle = it.originalTitle,
                            genres = it.genres,
                            countryOfOrigin = it.countries,
                            releaseYear = it.year,
                            runTimeInMinutes = it.runtime,
                            cast = it.cast,
                            movieDescription = it.overview,
                            originalLanguage = it.originalLanguage,
                            posterURL = POSTER_HOST + it.posterPath,
                            streamingProvider = null
                        )
                        adapterData.add(movie)
                    }
                    updateMoviesOnUI()
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
    private fun updateMoviesOnUI() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(adapterData, mainActivity)
        }
    }

    private fun clearMoviesOnUI() {
        adapterData.clear()

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(adapterData, mainActivity)
        }
    }

    override fun onClick(movie: MovieClass) {
        val intent = Intent(applicationContext, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID, movie.id)
        startActivity(intent)
    }

    private fun executeSearch() {

        val searchKeyword = binding.etMovieSearch.text?.toString()

        if (searchKeyword != null && searchKeyword != "") {
            clearMoviesOnUI()
            searchMovies(
                apiService = apiService,
                country = country,
                service = service,
                keyword = searchKeyword
            )

            closeKeyboard()

        } else {
            Toast.makeText(this, "Please enter your keyword before searching", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}