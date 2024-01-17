package com.example.m88demoapp.actionHandler

import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.model.MovieDetails

/**
 * Interface defining actions related to movie items.
 */
interface MovieActionHandler {
    /**
     * Called when a movie item is clicked.
     *
     * @param item The clicked [MovieDetails] item.
     */
    fun onMovieItemClick(item: MovieDetails)

    /**
     * Called when a movie item is marked as saved.
     *
     * @param item The [MovieDetails] item to be saved.
     */
    fun onMovieItemSave(item: MovieDetails)

    /**
     * Retrieves the list of saved movies.
     *
     * @return List of saved movies as [MovieDetailsEntity], or null if not available.
     */
    fun getSavedMovies(): List<MovieDetailsEntity>?

    /**
     * Deletes a saved movie based on its track ID.
     *
     * @param trackId The track ID of the movie to be deleted.
     */
    fun deleteSavedMovies(trackId: Long?)
}
