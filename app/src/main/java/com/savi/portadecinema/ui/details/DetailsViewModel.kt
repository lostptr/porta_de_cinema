package com.savi.portadecinema.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savi.portadecinema.models.MovieDetails
import com.savi.portadecinema.repositories.MovieRepository
import com.savi.portadecinema.services.tmdb.TmdbService
import com.savi.portadecinema.services.tmdb.dto.MovieDetailsDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val _details = MutableStateFlow(MovieDetails(0, "", "", 0f, "", listOf(), false))
    val details: StateFlow<MovieDetails> = _details

    fun loadDetails(movieId: Int) {
        viewModelScope.launch(dispatcher) {
            movieRepository.getDetails(movieId).enqueue(object : Callback<MovieDetailsDto> {
                override fun onResponse(
                    call: Call<MovieDetailsDto>,
                    response: Response<MovieDetailsDto>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.apply {
                            _details.value = MovieDetails(
                                id,
                                title,
                                overview,
                                voteAvg,
                                TmdbService.getImageFullPath(backdropPath),
                                genres.map { g -> g.name },
                                false
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetailsDto>, t: Throwable) {
                    Log.e(DetailsViewModel::class.java.name, "Erro ao recuperar o filme selecionado (id: $movieId")
                }
            })
        }
    }
}