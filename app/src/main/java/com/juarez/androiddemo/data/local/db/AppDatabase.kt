package com.juarez.androiddemo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juarez.androiddemo.domain.models.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}