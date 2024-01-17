package com.example.m88demoapp.repository

import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.iTuneEntity.SavedDataEntity
import com.example.m88demoapp.response.ItunesResponse

/**
 * Repository interface defining the contract for interacting with the iTunes API
 * and managing local data storage using Room database.
 */
interface ItunesRepository {

    /**
     * Search for movies using the iTunes API.
     * @return [ItunesResponse] containing the search results.
     */
    suspend fun searchMovies(): ItunesResponse

    /**
     * Retrieve all saved movie details from the local storage.
     * @return List of [MovieDetailsEntity].
     */
    suspend fun getAllSavedMovies(): List<MovieDetailsEntity>

    /**
     * Save movie details into the local storage.
     * @param movieDetails [MovieDetailsEntity] to be saved.
     */
    suspend fun saveMovieDetails(movieDetails: MovieDetailsEntity)

    /**
     * Delete movie details with the given track ID from the local storage.
     * @param trackId Track ID of the movie to be deleted.
     */
    suspend fun deleteMovieDetails(trackId: Long)

    /**
     * Save temporary data into the local storage.
     * @param savedData [SavedDataEntity] to be saved.
     */
    suspend fun saveSavedData(savedData: SavedDataEntity)

    /**
     * Retrieve the saved temporary data from the local storage.
     * @return [SavedDataEntity] or null if no data is saved.
     */
    suspend fun getSavedData(): SavedDataEntity?

    /**
     * Remove saved temporary data from the local storage.
     * @param savedData [SavedDataEntity] to be removed.
     */
    suspend fun removeSavedData(savedData: SavedDataEntity)
}

