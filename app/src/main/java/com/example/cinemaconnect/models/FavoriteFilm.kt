package com.example.cinemaconnect.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteFilm(
    @PrimaryKey
    val movieId: String,
    val title: String,
    val description: String,
    val poster: String,
    val imdb: Int,
    val year: Int,
    val price: Double
)
