package com.juarez.upaxdemo.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juarez.upaxdemo.domain.models.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(vararg movies: MovieEntity)

    @Query("SELECT * from movie_table WHERE category = 'popular'")
    fun getPopularMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * from movie_table WHERE category = 'top'")
    fun getTopRatedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT COUNT(*) from movie_table WHERE category = 'popular'")
    suspend fun getTotalPopularMovies(): Int

    @Query("SELECT COUNT(*) from movie_table WHERE category = 'top'")
    suspend fun getTotalTopRatedMovies(): Int

    @Query("DELETE from movie_table")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM movie_table WHERE movieId = :movieId")
    suspend fun removeMovieById(movieId: Int)

    @Query("SELECT * FROM movie_table WHERE movieId = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?
}