package com.example.cinemaconnect.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.cinemaconnect.models.FavoriteFilm
import com.example.cinemaconnect.models.Film
import com.example.cinemaconnect.repository.FavoritesRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(
    application: Application,
    private val repository: FavoritesRepository
) : AndroidViewModel(application) {

    val allFavorites: LiveData<List<FavoriteFilm>> = repository.allFavorites

    fun addFavorite(movie: Film) = viewModelScope.launch {
        repository.addMovie(movie)
    }

    fun removeFavorite(movie: FavoriteFilm) = viewModelScope.launch {
        repository.removeMovie(movie)
    }

    fun isFavorite(movieId: String): LiveData<Boolean> = liveData {
        emit(repository.isFavorite(movieId))
    }
}