package com.daurelio.m_ov_ies


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toolbar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainActivity = this

        val settings: SharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)

        if (!MovieSettingsActivity().checkSettings(settings)) {
            val intent = Intent(this, MovieSettingsActivity::class.java)
            startActivity(intent)
        }


        searchMovies(
            apiService = apiService,
        )

        binding.btnSearch.setOnClickListener {
            executeSearch()
        }

        binding.tbMainActivity.menu.findItem(R.id.ab_settings).setOnMenuItemClickListener {
            val intent = Intent(applicationContext, MovieSettingsActivity::class.java)
            startActivity(intent)
            true
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
        keyword: String? = null
    ) {

        val settings: SharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        var country: String? = null

        if (settings.getBoolean("radioButtonGermany", false)) {
            country = "de"
        }
        if (settings.getBoolean("radioButtonUSA", false)) {
            country = "us"
        }


        val services = mutableListOf<String>()

        if (settings.getBoolean("checkboxNetflix", false)) {
            services.add("netflix")
        }
        if (settings.getBoolean("checkboxPrime", false)) {
            services.add("prime")
        }
        if (settings.getBoolean("checkboxDisney", false)) {
            services.add("disney")
        }
        if (settings.getBoolean("checkboxHBO", false)) {
            services.add("hbo")
        }
        if (settings.getBoolean("checkboxHulu", false)) {
            services.add("hulu")
        }
        if (settings.getBoolean("checkboxPeacock", false)) {
            services.add("peacock")
        }
        if (settings.getBoolean("checkboxParamount", false)) {
            services.add("paramount")
        }
        if (settings.getBoolean("checkboxStarz", false)) {
            services.add("starz")
        }
        if (settings.getBoolean("checkboxShowtime", false)) {
            services.add("showtime")
        }
        if (settings.getBoolean("checkboxApple", false)) {
            services.add("apple")
        }
        if (settings.getBoolean("checkboxMubi", false)) {
            services.add("mubi")
        }



        services.forEach {
            // The API call to Basic Search with given parameters
            val call = apiService.getSearch(
                country = country!!,
                service = it,
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
                                streamingProvider = it.streamingInfo
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


    }

    //Fill recycler view with movie cards
    private fun updateMoviesOnUI() {
        binding.rlLoading.visibility = GONE
        binding.recyclerView.visibility = VISIBLE

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(adapterData, mainActivity)
        }
    }

    private fun clearMoviesOnUI() {
        adapterData.clear()

        binding.rlLoading.visibility = VISIBLE
        binding.recyclerView.visibility = GONE

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(adapterData, mainActivity)
        }
    }

    private fun executeSearch() {

        val searchKeyword = binding.etMovieSearch.text?.toString()

        if (searchKeyword != null && searchKeyword != "") {
            clearMoviesOnUI()
            searchMovies(
                apiService = apiService,
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

    //On Detail card click
    override fun onClick(movie: MovieClass) {
        val intent = Intent(applicationContext, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID, movie.id)
        startActivity(intent)
    }
}