package com.example.cinemaconnect.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinemaconnect.models.FavoriteFilm

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieToFavorites(movie: FavoriteFilm)

    @Delete
    suspend fun removeMovieFromFavorites(movie: FavoriteFilm)

    @Query("SELECT * FROM favorites")
    fun getFavoriteMovies(): LiveData<List<FavoriteFilm>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE movieId = :id)")
    suspend fun isMovieFavorite(id: String): Boolean

}