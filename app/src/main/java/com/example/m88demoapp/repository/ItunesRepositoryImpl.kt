package com.example.m88demoapp.repository

import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.iTuneEntity.SavedDataEntity
import com.example.m88demoapp.iTunesDao.MovieDetailsDao
import com.example.m88demoapp.iTunesDao.SavedDataDao
import com.example.m88demoapp.response.ItunesResponse
import com.example.m88demoapp.service.ItunesApiService

/**
 * Implementation of [ItunesRepository] responsible for interacting with the iTunes API
 * and managing local data storage using Room database.
 *
 * @param apiService Instance of [ItunesApiService] for making API requests.
 * @param movieDetailsDao Data Access Object for [MovieDetailsEntity] in Room database.
 * @param savedDataDao Data Access Object for [SavedDataEntity] in Room database.
 */
class ItunesRepositoryImpl(
    private val apiService: ItunesApiService,
    private val movieDetailsDao: MovieDetailsDao,
    private val savedDataDao: SavedDataDao
) : ItunesRepository {

    /**
     * Search for movies using the iTunes API.
     * @return [ItunesResponse] containing the search results.
     */
    override suspend fun searchMovies(): ItunesResponse {
        val response = apiService.searchMovies()
        response.results
        return response
    }

    /**
     * Save movie details into the local Room database.
     * @param movieDetails [MovieDetailsEntity] to be saved.
     */
    override suspend fun saveMovieDetails(movieDetails: MovieDetailsEntity) {
        movieDetailsDao.insertMovieDetails(movieDetails)
    }

    /**
     * Retrieve all saved movie details from the local Room database.
     * @return List of [MovieDetailsEntity].
     */
    override suspend fun getAllSavedMovies(): List<MovieDetailsEntity> {
        return movieDetailsDao.getAllMovieDetails()
    }

    /**
     * Delete movie details with the given track ID from the local Room database.
     * @param trackId Track ID of the movie to be deleted.
     */
    override suspend fun deleteMovieDetails(trackId: Long) {
        movieDetailsDao.deleteMovieDetails(trackId)
    }

    /**
     * Save temporary data into the local Room database.
     * @param savedData [SavedDataEntity] to be saved.
     */
    override suspend fun saveSavedData(savedData: SavedDataEntity) {
        savedDataDao.saveData(savedData)
    }

    /**
     * Retrieve the saved temporary data from the local Room database.
     * @return [SavedDataEntity] or null if no data is saved.
     */
    override suspend fun getSavedData(): SavedDataEntity? {
        return savedDataDao.getData()
    }

    /**
     * Remove saved temporary data from the local Room database.
     * @param savedData [SavedDataEntity] to be removed.
     */
    override suspend fun removeSavedData(savedData: SavedDataEntity) {
        savedDataDao.removeData(savedData)
    }
}

