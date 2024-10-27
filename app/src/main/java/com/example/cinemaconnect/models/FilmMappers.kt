package com.example.cinemaconnect.models

fun Film.toFavoriteFilm(movieId: String): FavoriteFilm {
    return FavoriteFilm(
        movieId = movieId,
        title = this.Title ?: "",
        description = this.Description ?: "",
        poster = this.Poster ?: "",
        imdb = this.Imdb,
        year = this.Year,
        price = this.Price
    )
}

fun FavoriteFilm.toFilm(): Film {
    return Film(
        Title = this.title,
        Description = this.description,
        Poster = this.poster,
        Imdb = this.imdb,
        Year = this.year,
        Price = this.price
    )
}
