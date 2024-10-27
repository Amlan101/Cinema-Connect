package com.example.cinemaconnect.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemaconnect.repository.FavoritesRepository
import com.example.cinemaconnect.viewmodel.FavoritesViewModel

class FavoritesViewModelFactory(
    private val application: Application,
    private val repository: FavoritesRepository
): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoritesViewModel::class.java)){
            return FavoritesViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}