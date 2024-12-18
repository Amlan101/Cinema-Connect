package com.example.cinemaconnect.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinemaconnect.adapters.FavoritesAdapter
import com.example.cinemaconnect.data.CinemaDatabase
import com.example.cinemaconnect.databinding.FragmentFavoritesBinding
import com.example.cinemaconnect.factory.FavoritesViewModelFactory
import com.example.cinemaconnect.models.FavoriteFilm
import com.example.cinemaconnect.models.toFilm
import com.example.cinemaconnect.presentation.activities.FilmDetailActivity
import com.example.cinemaconnect.repository.FavoritesRepository
import com.example.cinemaconnect.viewmodel.FavoritesViewModel
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =  FragmentFavoritesBinding.inflate(inflater, container, false)

        // Set up ViewModel with Factory
        val favoritesDao = CinemaDatabase.getDatabase(requireContext()).favoritesDao()
        val repository = FavoritesRepository(favoritesDao)
        val factory = FavoritesViewModelFactory(requireActivity().application, repository)
        favoritesViewModel = ViewModelProvider(this, factory).get(FavoritesViewModel::class.java)

        // Observe the list of favorite movies
        favoritesViewModel.allFavorites.observe(viewLifecycleOwner){ favoriteMovies ->

            binding.favouritesRecyclerView.layoutManager = GridLayoutManager(context, 2)
            binding.favouritesRecyclerView.itemAnimator = SlideInLeftAnimator()

            // Set up the adapter with item and remove click listeners
            binding.favouritesRecyclerView.adapter = FavoritesAdapter(
                favoriteMovies,
                onItemClick = { movie ->
                    openFilmDetailActivity(movie)
                },
                onItemRemoveClick = { movie ->
                    // Remove from favorites
                    favoritesViewModel.removeFavorite(movie)
                }
            )
            binding.favoritesProgressBar.visibility = if (favoriteMovies.isEmpty()) View.VISIBLE else View.GONE
        }
        return binding.root
    }

    private fun openFilmDetailActivity(favoriteFilm: FavoriteFilm) {

        val intent = Intent(requireContext(), FilmDetailActivity::class.java).apply {
            putExtra("object", favoriteFilm.toFilm())
        }
        startActivity(intent)
        Log.d("FavoriteFilmDetail", "Opening FilmDetailActivity with title: ${favoriteFilm.title}")
    }
}