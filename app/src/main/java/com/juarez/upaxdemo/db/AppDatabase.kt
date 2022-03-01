package com.juarez.upaxdemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juarez.upaxdemo.movies.data.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}