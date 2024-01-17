package com.example.m88demoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.iTuneEntity.SavedDataEntity
import com.example.m88demoapp.model.MovieDetails
import com.example.m88demoapp.repository.PreferenceRepository
import com.example.m88demoapp.repository.ItunesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel responsible for managing data related to iTunes movies and user preferences.
 *
 * @param repository The repository providing access to iTunes movie data.
 * @param appRepository The repository managing application-level preferences.
 */
class ItunesViewModel(
    private val repository: ItunesRepository,
    private val appRepository: PreferenceRepository
) : ViewModel() {

    // LiveData for observing the list of iTunes movies
    private val _movieList = MutableLiveData<List<MovieDetails>>()
    val movieList: LiveData<List<MovieDetails>> get() = _movieList

    // LiveData for observing search results based on user input
    private val _searchResults = MutableLiveData<List<MovieDetails>>()
    val searchResults: LiveData<List<MovieDetails>> get() = _searchResults

    // LiveData for observing the list of saved movies
    private val _savedMovies = MutableLiveData<List<MovieDetailsEntity>>()
    val savedMovies: LiveData<List<MovieDetailsEntity>> get() = _savedMovies

    // LiveData for observing temporarily saved movie details
    private val _savedTemporaryMovies = MutableLiveData<SavedDataEntity>()
    val savedTemporaryMovies: LiveData<SavedDataEntity> get() = _savedTemporaryMovies

    // Initialization block fetching initial movie data

    /**
     * Fetches the initial list of iTunes movies.
     */
    fun fetchMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.searchMovies()
                _movieList.postValue(response.results)
            } catch (e: Exception) {
                // Handle the exception, for example, log it or show an error message
                _movieList.postValue(emptyList()) // Empty list or any default value as needed
            }
        }
    }

    /**
     * Searches for movies based on the user's input text.
     *
     * @param query The search query provided by the user.
     */
    fun searchMoviesByText(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val results = _movieList.value?.filter {
                it.trackName?.contains(query, ignoreCase = true) ?: false
            } ?: emptyList()
            _searchResults.postValue(results)
        }
    }

    /**
     * Saves the provided movie details to the user's favorites.
     *
     * @param movieDetails The details of the movie to be saved.
     */
    suspend fun saveMovieDetails(movieDetails: MovieDetailsEntity) {
        repository.saveMovieDetails(movieDetails)

        getSavedMovies()
    }

    /**
     * Retrieves the list of saved movies.
     */
    suspend fun getSavedMovies() {
        _savedMovies.value = repository.getAllSavedMovies()
    }

    /**
     * Deletes the saved movie with the specified trackId.
     *
     * @param trackId The unique identifier of the movie to be deleted.
     */
    suspend fun deleteSavedMovie(trackId: Long?) {
        viewModelScope.launch {
            if (trackId != null) {
                repository.deleteMovieDetails(trackId)
                getSavedMovies()
            }
        }
    }

    /**
     * Retrieves the last visit time of the app.
     *
     * @return The timestamp of the last visit time.
     */
    suspend fun getLastVisitTime(): Long {
        return withContext(Dispatchers.IO) {
            appRepository.getLastVisitTime()
        }
    }

    /**
     * Saves temporarily provided movie details.
     *
     * @param savedData The details of the movie to be temporarily saved.
     */
    suspend fun savedTemporaryMovieDetail(savedData: SavedDataEntity) {
        repository.saveSavedData(savedData)
    }

    /**
     * Retrieves the temporarily saved movie details.
     */
    suspend fun getSavedTemporaryMovies() {
        _savedTemporaryMovies.value = repository.getSavedData()
    }

    /**
     * Deletes the temporarily saved movie details.
     *
     * @param savedData The details of the movie to be removed.
     */
    suspend fun deleteSavedMovie(savedData: SavedDataEntity) {
        viewModelScope.launch {
            repository.removeSavedData(savedData)
        }
    }

    /**
     * Saves the ID of the last screen visited by the user.
     *
     * @param screenId The ID of the last screen visited.
     */
    suspend fun saveLastScreen(screenId: Int) {
        appRepository.saveLastScreen(screenId)
    }

    /**
     * Retrieves the ID of the last screen visited by the user.
     *
     * @return The ID of the last screen visited.
     */
    suspend fun getLastScreen(): Int {
        return withContext(Dispatchers.IO) {
            appRepository.getLastScreen()
        }
    }
}

