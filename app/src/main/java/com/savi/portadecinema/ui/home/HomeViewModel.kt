package com.savi.portadecinema.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.savi.portadecinema.models.MovieOutline
import com.savi.portadecinema.repositories.MovieRepository
import com.savi.portadecinema.services.tmdb.TmdbService
import com.savi.portadecinema.services.tmdb.dto.MoviePageDto
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private var lastPageLoaded: Int = 0
    private val _movies = MutableStateFlow<List<MovieOutline>>(listOf())
    val movies: StateFlow<List<MovieOutline>> = _movies

    fun loadNextMoviePage() {
        viewModelScope.launch(dispatcher) {
            movieRepository.getPopular(lastPageLoaded + 1).enqueue(object : Callback<MoviePageDto> {
                override fun onResponse(
                    call: Call<MoviePageDto>,
                    response: Response<MoviePageDto>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.results?.let { movieList ->
                            lastPageLoaded += 1
                            Log.i(
                                "SCROLL_TEST",
                                "filmes encontrados $lastPageLoaded"
                            )
                            _movies.value = movieList.map { m ->
                                MovieOutline(
                                    m.id,
                                    m.title,
                                    m.overview,
                                    m.voteAverage,
                                    TmdbService.getImageFullPath(m.posterPath)
                                )
                            }
                        }
                    } else {
                        Log.e(
                            "SCROLL_TEST",
                            "Requisição rejeitada (pag. ${lastPageLoaded + 1}) -> ${response.code()}: ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<MoviePageDto>, t: Throwable) {
                    Log.e(
                        "SCROLL_TEST",
                        "Erro ao recuperar a lista de filmes (pag. ${lastPageLoaded + 1})"
                    )
                }
            })
        }
    }
}