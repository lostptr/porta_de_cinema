package com.savi.portadecinema.ui.details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.savi.portadecinema.databinding.ActivityDetailsBinding
import com.savi.portadecinema.helpers.setFullscreen
import com.savi.portadecinema.models.MovieDetails
import com.savi.portadecinema.repositories.MovieRepository
import com.savi.portadecinema.services.tmdb.TmdbService

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFullscreen()

        viewModel =
            ViewModelProvider(
                this,
                DetailsViewModelFactory(MovieRepository(TmdbService.getInstance()))
            ).get(DetailsViewModel::class.java)

        setObservers()
    }

    private fun setObservers() {
        // On Resume
        lifecycleScope.launchWhenResumed {
            viewModel.details.collect {details ->
               load(details)
            }
        }

        // On Create
        lifecycleScope.launchWhenCreated {
            val id = intent.getIntExtra(TAG_MOVIE_ID, 0)
            if (id != 0) {
                viewModel.loadDetails(id)
            } else {
                Log.e(DetailsActivity::class.java.name, "Id não pode ser zero.")
            }
        }
    }

    private fun load(movie: MovieDetails) {
        with(binding) {
            detailsTitleTextview.text = movie.title
            detailsOverviewTextview.text = movie.overview
            detailsGenresTextview.text = movie.genres.joinToString(separator = " • ")
            detailsFavoriteCheckbox.isChecked = movie.isFavorite
        }

        Glide.with(this)
            .load(movie.poster)
            .centerCrop()
            .into(binding.detailsPoster)
    }

    companion object {
        const val TAG_MOVIE_ID = "MOVIE_ID"
    }
}