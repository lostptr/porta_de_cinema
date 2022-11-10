package com.savi.portadecinema.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.savi.portadecinema.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModel<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)

        setObservers()

        return binding.root
    }

    private fun setObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.favoriteMovies().collect {movies ->
                binding.recyclerViewFavorites.adapter = FavoriteMovieAdapter(movies)
            }
        }
    }


}