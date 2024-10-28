package com.example.cinemaconnect.repository

import androidx.lifecycle.LiveData
import com.example.cinemaconnect.data.FavoritesDao
import com.example.cinemaconnect.models.FavoriteFilm
import com.example.cinemaconnect.models.Film
import com.example.cinemaconnect.models.toFavoriteFilm

class FavoritesRepository(private val favoritesDao: FavoritesDao) {

    val allFavorites: LiveData<List<FavoriteFilm>> = favoritesDao.getFavoriteMovies()

    suspend fun addMovie(movie: Film) {
        val favoriteMovie = movie.firebaseId?.let { movie.toFavoriteFilm(movieId = it) }
        if (favoriteMovie != null) {
            favoritesDao.addMovieToFavorites(favoriteMovie)
        }
    }

    suspend fun removeMovie(movie: FavoriteFilm) {
        favoritesDao.removeMovieFromFavorites(movie)
    }

    suspend fun isFavorite(movieId: String): Boolean {
        return favoritesDao.isMovieFavorite(movieId)
    }
}
