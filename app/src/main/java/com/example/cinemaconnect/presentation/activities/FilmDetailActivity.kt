package com.example.cinemaconnect.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.cinemaconnect.R
import com.example.cinemaconnect.adapters.CastListAdapter
import com.example.cinemaconnect.adapters.CategoryEachFilmAdapter
import com.example.cinemaconnect.data.CinemaDatabase
import com.example.cinemaconnect.databinding.ActivityFilmDetailBinding
import com.example.cinemaconnect.factory.FavoritesViewModelFactory
import com.example.cinemaconnect.models.Film
import com.example.cinemaconnect.repository.FavoritesRepository
import com.example.cinemaconnect.viewmodel.FavoritesViewModel
import eightbitlab.com.blurview.RenderScriptBlur

class FilmDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilmDetailBinding
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var film: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoritesDao = CinemaDatabase.getDatabase(application).favoritesDao()
        val repository = FavoritesRepository(favoritesDao)
        val factory = FavoritesViewModelFactory(application, repository)

        favoritesViewModel = ViewModelProvider(this, factory).get(FavoritesViewModel::class.java)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setVariable()
        setFavorites()
    }

    private fun setFavorites() {
        film = intent.getParcelableExtra("object")!!

        // Observe the initial favorite state and update the icon accordingly
        film.firebaseId?.let { movieId ->
            favoritesViewModel.isFavorite(movieId).observe(this) { isFavorite ->
                binding.favoriteBtn.setImageResource(
                    if (isFavorite) R.drawable.favorite_filled_24 else R.drawable.btn_2
                )
            }

            // Toggle favorite state on button click
            binding.favoriteBtn.setOnClickListener {
                // Check the current icon state and toggle it immediately
                val isCurrentlyFavorite = binding.favoriteBtn.drawable.constantState ==
                        resources.getDrawable(R.drawable.favorite_filled_24).constantState

                if (isCurrentlyFavorite) {
                    // Remove from favorites and update the icon immediately
                    favoritesViewModel.removeFavorite(film)
                    binding.favoriteBtn.setImageResource(R.drawable.btn_2)
                } else {
                    // Add to favorites and update the icon immediately
                    favoritesViewModel.addFavorite(film)
                    binding.favoriteBtn.setImageResource(R.drawable.favorite_filled_24)
                }
            }
        }
    }

    private fun setVariable() {
        val item: Film = intent.getParcelableExtra("object")!!
        val requestOptions =
            RequestOptions()
                .transform(CenterCrop(), GranularRoundedCorners(0f, 0f, 50f, 50f))

        Glide.with(this)
            .load(item.Poster)
            .apply(requestOptions)
            .into(binding.filmPicImgView)

        binding.titleFilmDetailTxtView.text = item.Title
        binding.movieTimeTxtView.text = "${item.Year} - ${item.Time}"
        binding.imdbFilmDetailTxtView.text = "IMDB: ${item.Imdb}"
        binding.movieSummaryTxtView.text = item.Description

        binding.backBtnImgView.setOnClickListener {
            finish()
        }

        binding.buyTicketBtn.setOnClickListener {
            val intent = Intent(this, SeatListActivity::class.java)
            intent.putExtra("film", item)
            startActivity(intent)
        }

        val radius = 10f
        val decorView = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowsBackground = decorView.background

        binding.blurView.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowsBackground)
            .setBlurRadius(radius)

        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true

        item.Genre?.let {
            binding.genreRecyclerView.adapter = CategoryEachFilmAdapter(it)
            binding.genreRecyclerView.layoutManager =
                LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }

        item.Casts?.let {
            binding.castRecyclerView.adapter = CastListAdapter(it)
            binding.castRecyclerView.layoutManager =
                LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }

    }
}