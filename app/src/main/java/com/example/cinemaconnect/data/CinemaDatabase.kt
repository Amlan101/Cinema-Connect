package com.example.cinemaconnect.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinemaconnect.models.FavoriteFilm

@Database(entities = [FavoriteFilm::class], version = 1, exportSchema = false)
abstract class CinemaDatabase: RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    companion object{
        @Volatile
        private var INSTANCE: CinemaDatabase? = null

        fun getDatabase(context: Context): CinemaDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CinemaDatabase::class.java,
                    "cinema_connect_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}