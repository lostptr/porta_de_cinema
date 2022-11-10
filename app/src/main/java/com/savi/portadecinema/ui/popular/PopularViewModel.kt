package com.savi.portadecinema.ui.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savi.portadecinema.data.remote.tmdb.TmdbService
import com.savi.portadecinema.data.remote.tmdb.dto.MoviePageDto
import com.savi.portadecinema.models.MovieOutline
import com.savi.portadecinema.repositories.IMovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularViewModel(private val movieRepository: IMovieRepository) : ViewModel() {

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