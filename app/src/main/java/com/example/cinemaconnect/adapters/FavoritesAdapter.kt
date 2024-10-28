package com.example.cinemaconnect.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemaconnect.databinding.ItemFavoriteFilmBinding
import com.example.cinemaconnect.models.FavoriteFilm

class FavoritesAdapter(
    private val favoriteMovies: List<FavoriteFilm>,
    private val onItemClick: (FavoriteFilm) -> Unit
): RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    inner class FavoritesViewHolder(private val binding: ItemFavoriteFilmBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(film: FavoriteFilm) {
            binding.favoriteMovieTitle.text = film.title
            Glide.with(binding.root.context)
                .load(film.poster)
                .into(binding.favoriteMoviePoster)

            binding.root.setOnClickListener {
                onItemClick(film)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesViewHolder {
        val binding = ItemFavoriteFilmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.FavoritesViewHolder, position: Int) {
        holder.bind(favoriteMovies[position])
    }

    override fun getItemCount(): Int {
        return favoriteMovies.size
    }

}