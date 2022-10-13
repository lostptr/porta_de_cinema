package com.savi.portadecinema.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.savi.portadecinema.R
import com.savi.portadecinema.helpers.MovieOutlineDiff
import com.savi.portadecinema.models.MovieOutline
import com.savi.portadecinema.ui.details.DetailsActivity

class MovieAdapter(private var movies: List<MovieOutline>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: MovieOutline) {
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

            val card = view.findViewById<MaterialCardView>(R.id.card_movie)
            card.setOnClickListener { onClick(movie) }
        }

        private fun onClick(movie: MovieOutline) {
            val intent = Intent(view.context, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.TAG_MOVIE_ID, movie.id)
            view.context.startActivity(intent)
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

    fun update(newMovies: List<MovieOutline>) {
        val callback = MovieOutlineDiff(movies, newMovies)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(this)
        movies = newMovies
    }

    fun append(newMovies: List<MovieOutline>) = update(movies + newMovies)
}