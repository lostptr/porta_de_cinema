package com.savi.portadecinema.ui.details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.savi.portadecinema.databinding.ActivityDetailsBinding
import com.savi.portadecinema.models.MovieDetails
import com.savi.portadecinema.utils.CustomFormatting
import com.savi.portadecinema.utils.setFullscreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel by viewModel<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFullscreen()

        setObservers()
    }

    private fun setObservers() {
        // On Create
        lifecycleScope.launchWhenCreated {
            val id = intent.getIntExtra(TAG_MOVIE_ID, 0)
            if (id != 0) {
                viewModel.loadDetails(id)
            } else {
                Log.e(DetailsActivity::class.java.name, "Id não pode ser zero.")
            }
        }

        // On Resume
        lifecycleScope.launchWhenResumed {
            viewModel.details.collect { details ->
                load(details)
            }
        }

        // Favorite click
        binding.detailsFavoriteCheckbox.setOnClickListener {
            viewModel.toggleFavorite()
            val message = if (viewModel.details.value.isFavorite) "Removido dos favoritos" else "Adicionado aos favoritos"
            Snackbar.make(binding.detailsFavoriteCheckbox, message, Snackbar.LENGTH_LONG)
                .setAction("Desfazer") {
                    viewModel.toggleFavorite()
                }
                .show()
        }

        // Back button
        binding.detailsBackButton.setOnClickListener {
           back()
        }
    }

    private fun load(movie: MovieDetails) {
        if (movie.id != 0) {
            with(binding) {
                detailsTitleTextview.text = movie.title
                detailsOverviewTextview.text = movie.overview
                detailsGenresTextview.text = movie.genres.joinToString(separator = " • ")
                detailsFavoriteCheckbox.isChecked = movie.isFavorite
                detailsReleaseTextview.text =
                    CustomFormatting.format(movie.releaseDate, "dd/MM/yyyy")
                detailsRatingTextview.text = "%.1f".format(movie.rating)
                detailsDurationTextview.text = CustomFormatting.formatDuration(movie.duration)
            }

            Glide.with(this)
                .load(movie.backdrop)
                .centerCrop()
                .into(binding.detailsPoster)
        }
    }

    private fun back() {
        finish()
    }

    companion object {
        const val TAG_MOVIE_ID = "MOVIE_ID"
    }
}