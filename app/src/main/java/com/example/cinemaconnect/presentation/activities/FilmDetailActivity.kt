package com.example.cinemaconnect.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemaconnect.databinding.ActivityFilmDetailBinding

class FilmDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilmDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}