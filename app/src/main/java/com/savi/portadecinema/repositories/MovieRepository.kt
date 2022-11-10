package com.savi.portadecinema.repositories

import com.savi.portadecinema.data.local.dao.MovieDao
import com.savi.portadecinema.data.local.entities.MovieEntity
import com.savi.portadecinema.data.remote.tmdb.TmdbService
import com.savi.portadecinema.models.MovieDetails

class MovieRepository(private val tmdbService: TmdbService, private val dao: MovieDao) :
    IMovieRepository {

    override fun getPopular(page: Int) = tmdbService.getPopularMovies(page)

    override fun getDetails(movieId: Int) = tmdbService.getMovieDetails(movieId)

    override fun getFavorites() = dao.getAll()

    override suspend fun saveAsFavorite(movie: MovieDetails) =
        dao.insert(with(movie) {
            MovieEntity(
                id = 0,
                tmdbId = id,
                title = title,
                overview = overview,
                rating = rating,
                duration = duration,
                genres = genres.joinToString(","),
                releaseDate = releaseDate.toString(),
                poster = poster,
                backdrop = backdrop
            )
        })

    override suspend fun removeFavorite(movieId: Int) = dao.delete(movieId)

    override suspend fun checkIsFavorite(movieId: Int) = dao.checkIsFavorite(movieId)

}