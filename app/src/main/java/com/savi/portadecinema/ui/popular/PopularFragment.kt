package com.savi.portadecinema.ui.popular

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.savi.portadecinema.databinding.FragmentPopularBinding
import com.savi.portadecinema.utils.PaginationManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private val viewModel by viewModel<PopularViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater)

        binding.recyclerViewMovies.adapter = PopularMovieAdapter(listOf())

        setObservers()
        addListeners()
        loadNextPage() // Carregar filmes pela primeira vez

        return binding.root
    }

    private fun setObservers() {
        lifecycleScope.launchWhenResumed {
            startLoading()
            viewModel.movies.collect { movies ->
                if (movies.isNotEmpty()) {
                    binding.recyclerViewMovies.adapter?.let {
                        (it as PopularMovieAdapter).append(movies)
                        stopLoading()
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

    private fun startLoading() {
        binding.recyclerViewMovies.visibility = View.GONE
        binding.popularShimmerLayout.showShimmer(true)
    }

    private fun stopLoading() {
        binding.popularShimmerLayout.stopShimmer()
        binding.popularShimmerLayout.visibility = View.INVISIBLE
        binding.recyclerViewMovies.visibility = View.VISIBLE
    }
}