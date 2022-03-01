package com.juarez.upaxdemo.di

import android.content.Context
import androidx.room.Room
import com.juarez.upaxdemo.db.AppDatabase
import com.juarez.upaxdemo.db.MovieDao
import com.juarez.upaxdemo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, Constants.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

}