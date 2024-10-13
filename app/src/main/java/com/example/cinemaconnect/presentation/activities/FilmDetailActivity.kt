package com.example.cinemaconnect.presentation.activities

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.cinemaconnect.R
import com.example.cinemaconnect.adapters.CastListAdapter
import com.example.cinemaconnect.adapters.CategoryEachFilmAdapter
import com.example.cinemaconnect.databinding.ActivityFilmDetailBinding
import com.example.cinemaconnect.models.Film
import eightbitlab.com.blurview.RenderScriptBlur

class FilmDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilmDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setVariable()
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