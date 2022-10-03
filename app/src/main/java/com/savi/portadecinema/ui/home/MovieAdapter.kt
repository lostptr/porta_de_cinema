package com.savi.portadecinema.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.savi.portadecinema.R
import com.savi.portadecinema.models.Movie

class MovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            val textViewTitle = view.findViewById<TextView>(R.id.movie_card_title)
            val textViewOverview = view.findViewById<TextView>(R.id.movie_card_overview)
            val textViewRating = view.findViewById<TextView>(R.id.movie_card_rating)
            val imageViewPoster = view.findViewById<ImageView>(R.id.movie_card_poster)

            with(movie) {
                textViewTitle.text = title
                textViewOverview.text = overview
                textViewRating.text = "Nota: $rating"
                Glide.with(view)
                    .load(poster)
                    .centerCrop()
                    .into(imageViewPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}