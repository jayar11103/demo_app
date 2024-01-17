package com.example.m88demoapp.iTunesDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity

/**
 * Data Access Object (DAO) for handling operations related to movie details in the local database.
 */
@Dao
interface MovieDetailsDao {
    /**
     * Insert or replace the provided [MovieDetailsEntity] in the database.
     *
     * @param movieDetails The movie details to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetailsEntity)

    /**
     * Retrieve all saved movie details from the database.
     *
     * @return A list of [MovieDetailsEntity] representing all saved movie details.
     */
    @Query("SELECT * FROM itunes_movie_list")
    suspend fun getAllMovieDetails(): List<MovieDetailsEntity>

    /**
     * Delete a specific movie detail from the database based on its [trackId].
     *
     * @param trackId The unique identifier of the movie detail to be deleted.
     */
    @Query("DELETE FROM itunes_movie_list WHERE trackId = :trackId")
    suspend fun deleteMovieDetails(trackId: Long)
}
