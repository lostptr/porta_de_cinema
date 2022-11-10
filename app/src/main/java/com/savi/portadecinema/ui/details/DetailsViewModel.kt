package com.savi.portadecinema.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savi.portadecinema.data.remote.tmdb.TmdbService
import com.savi.portadecinema.data.remote.tmdb.dto.MovieDetailsDto
import com.savi.portadecinema.di.repositoryModule
import com.savi.portadecinema.models.MovieDetails
import com.savi.portadecinema.repositories.IMovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class DetailsViewModel(private val movieRepository: IMovieRepository) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val _details =
        MutableStateFlow(MovieDetails(0, "", "", 0f, "", "",listOf(), LocalDate.MIN, 0, false))
    val details: StateFlow<MovieDetails> = _details

    fun loadDetails(movieId: Int) {
        viewModelScope.launch(dispatcher) {
            movieRepository.getDetails(movieId).enqueue(object : Callback<MovieDetailsDto> {
                override fun onResponse(
                    call: Call<MovieDetailsDto>,
                    response: Response<MovieDetailsDto>
                ) {
                    if (response.isSuccessful) {
                        viewModelScope.launch(dispatcher) {
                            response.body()?.apply {
                                _details.value = MovieDetails(
                                    id,
                                    title,
                                    overview,
                                    voteAvg,
                                    TmdbService.getImageFullPath(posterPath),
                                    TmdbService.getImageFullPath(backdropPath),
                                    genres.map { g -> g.name },
                                    releaseDate,
                                    runtimeMin,
                                    movieRepository.checkIsFavorite(id)
                                )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetailsDto>, t: Throwable) {
                    Log.e(
                        DetailsViewModel::class.java.name,
                        "Erro ao recuperar o filme selecionado (id: $movieId) ${t.message}"
                    )
                }
            })
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch(dispatcher) {
            if (_details.value.isFavorite) {
                movieRepository.removeFavorite(_details.value.id)
                _details.value = _details.value.copy(isFavorite = false)
            } else {
                movieRepository.saveAsFavorite(_details.value)
                _details.value = _details.value.copy(isFavorite = true)
            }
        }
    }
}