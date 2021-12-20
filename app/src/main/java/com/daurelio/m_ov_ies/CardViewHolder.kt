package com.daurelio.m_ov_ies

import androidx.recyclerview.widget.RecyclerView
import com.daurelio.m_ov_ies.databinding.CardCellBinding

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: MovieClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindMovie(movie: MovieClass) {
        cardCellBinding.tvMovieTitle.text = movie.originalMovieTitle
        cardCellBinding.tvMovieDesc.text = movie.movieDescription

        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(movie)
        }
    }

}