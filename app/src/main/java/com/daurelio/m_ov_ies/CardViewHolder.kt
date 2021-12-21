package com.daurelio.m_ov_ies

import androidx.recyclerview.widget.RecyclerView
import com.daurelio.m_ov_ies.databinding.CardCellBinding
import com.squareup.picasso.Picasso
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: MovieClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    //Set data for each card
    fun bindMovie(movie: MovieClass) {
        cardCellBinding.tvMovieTitle.text = movie.originalMovieTitle
        cardCellBinding.tvMovieDesc.text = movie.movieDescription

        Picasso.get().isLoggingEnabled = true
        Picasso.get()
            .load(movie.posterURL)
            .placeholder(R.drawable.ic_image_not_found)
            .resize(1000,1000)
            .centerInside()
            .into(cardCellBinding.ivThumbnail)



        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(movie)
        }
    }

}