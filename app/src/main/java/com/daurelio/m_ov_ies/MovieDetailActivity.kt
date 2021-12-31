/*
Author: Marco D'Aurelio
Purpose: Activity for the detail page. This is the activity that is being started when the
user clicks on any movie. This Activity has the functionality to display movies.
API Calls are NOT being executed from here.
*/

package com.daurelio.m_ov_ies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daurelio.m_ov_ies.databinding.ActivityMovieDetailBinding
import com.squareup.picasso.Picasso
import java.time.Duration
import java.time.LocalTime

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    //This function is used on the creation of the detail screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieID = intent.getStringExtra(MOVIE_ID)
        val movie = movieFromID(movieID!!)

        if (movie != null) {
            //Some preparation in order to display clean data
            var cast = movie.cast.toString()
            var countryOfOrigin = movie.countryOfOrigin.toString()

            if (cast == "[]") {
                cast = "N/A"
            }
            if (countryOfOrigin == "[]") {
                countryOfOrigin = "N/A"
            }


            //Display of movie data
            binding.tvMovieTitle.text = movie.originalMovieTitle
            binding.tvMovieDesc.text = movie.movieDescription

            binding.tvServicesContent.text =
                movie.streamingProvider.keys.toString().replace("[", "").replace("]", "")
                    .uppercase()
            binding.tvImdbIDContent.text = movie.imdbID
            binding.tvImdbRatingContent.text = movie.imdbRating.toString()
            binding.tvReleaseYearContent.text = movie.releaseYear.toString()
            binding.tvRuntimeContent.text =
                LocalTime.MIN.plus(Duration.ofMinutes(movie.runTimeInMinutes!!)).toString()
            binding.tvCountryOriginContent.text = countryOfOrigin.replace("[", "").replace("]", "")
            binding.tvOriginalTitleContent.text = movie.originalMovieTitle
            binding.tvOriginalLanguageContent.text = movie.originalLanguage
            binding.tvCastContent.text = cast.replace("[", "").replace("]", "")


            //Load image to the detail screen
            Picasso.get()
                .load(movie.posterURL)
                .placeholder(R.drawable.ic_image_not_found)
                .resize(1000, 1000)
                .centerInside()
                .into(binding.ivThumbnail)
        }

    }

    //Function that returns the movie of a given ID
    private fun movieFromID(movieID: String): MovieClass? {
        for (movie in adapterData) {
            if (movie.id == movieID) {
                return movie
            }
        }
        return null
    }
}