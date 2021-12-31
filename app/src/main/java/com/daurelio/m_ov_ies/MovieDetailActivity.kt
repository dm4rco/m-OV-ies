package com.daurelio.m_ov_ies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daurelio.m_ov_ies.databinding.ActivityMovieDetailBinding
import com.squareup.picasso.Picasso
import java.time.Duration
import java.time.LocalTime

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieID = intent.getStringExtra(MOVIE_ID)
        val movie = movieFromID(movieID!!)

        if (movie != null) {

            var cast = movie.cast.toString()
            var countryOfOrigin = movie.countryOfOrigin.toString()

            if (cast == "[]") {
                cast = "N/A"
            }
            if (countryOfOrigin == "[]") {
                countryOfOrigin = "N/A"
            }


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


            Picasso.get()
                .load(movie.posterURL)
                .placeholder(R.drawable.ic_image_not_found)
                .resize(1000, 1000)
                .centerInside()
                .into(binding.ivThumbnail)
        }

    }

    private fun movieFromID(movieID: String): MovieClass? {
        for (movie in adapterData) {
            if (movie.id == movieID) {
                return movie
            }
        }
        return null
    }
}