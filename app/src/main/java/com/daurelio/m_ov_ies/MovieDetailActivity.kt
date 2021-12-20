package com.daurelio.m_ov_ies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daurelio.m_ov_ies.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieID = intent.getIntExtra(MOVIE_ID_EXTRA, -1)
        val movie = movieFromID(movieID)

        if (movie != null) {
            binding.tvMovieTitle.text = movie.originalMovieTitle
            binding.tvMovieDesc.text = movie.movieDescription
        }

    }

    private fun movieFromID(movieID: Int): MovieClass? {
        for (movie in movieList) {
            if(movie.id == movieID) {
                return movie
            }
        }
        return null
    }
}