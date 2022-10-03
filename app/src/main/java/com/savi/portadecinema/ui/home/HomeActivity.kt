package com.savi.portadecinema.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.savi.portadecinema.databinding.ActivityHomeBinding
import com.savi.portadecinema.models.Movie
import com.savi.portadecinema.repositories.MovieRepository
import com.savi.portadecinema.services.tmdb.TmdbService


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(
                this,
                HomeViewModelFactory(MovieRepository(TmdbService.getInstance()))
            ).get(
                HomeViewModel::class.java
            )

        loadMovies()
    }

    private fun loadMovies() {
        viewModel.getPopularMovies(1).observe(this) { movies ->
            binding.recyclerViewMovies.adapter = MovieAdapter(movies)
        }
    }
}