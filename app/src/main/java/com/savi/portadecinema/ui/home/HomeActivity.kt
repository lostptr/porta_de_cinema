package com.savi.portadecinema.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.savi.portadecinema.databinding.ActivityHomeBinding
import com.savi.portadecinema.helpers.PaginationManager
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
                this, HomeViewModelFactory(MovieRepository(TmdbService.getInstance()))
            )[HomeViewModel::class.java]

        binding.recyclerViewMovies.adapter = MovieAdapter(listOf())

        setObservers()
        addListeners()
        loadNextPage() // Carregar filmes pela primeira vez.
    }

    private fun setObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.movies.collect { movies ->
                if (movies.isNotEmpty()) {
                    binding.recyclerViewMovies.adapter?.let {
                        (it as MovieAdapter).append(movies)
                    }
                }
            }
        }
    }

    private fun addListeners() {
        addScrollListener()
    }

    private fun addScrollListener() {
        // Sabemos que a API retorna 20 filmes por página.
        // Queremos começar a carregar os próximos quando o usuário já tiver rolado por 15 filmes.
        val pagination = PaginationManager(20, 9) {
            Log.i("SCROLL_TEST", "pagination manager -> reload trigger")
            loadNextPage()
        }

        binding.recyclerViewMovies.addOnScrollListener(pagination)
    }

    private fun loadNextPage() {
        viewModel.loadNextMoviePage()
    }
}