package com.juarez.upaxdemo.di

import android.content.Context
import androidx.room.Room
import com.juarez.upaxdemo.data.db.AppDatabase
import com.juarez.upaxdemo.data.db.MovieDao
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
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "upax.db").build()
    }

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

}