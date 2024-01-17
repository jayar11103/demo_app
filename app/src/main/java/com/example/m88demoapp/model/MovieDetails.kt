package com.example.m88demoapp.model

import android.os.Parcelable
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.iTuneEntity.SavedDataEntity
import kotlinx.android.parcel.Parcelize

/**
 * Parcelable data class representing details of a movie.
 * Implements [Parcelable] for easy data transfer between components.
 *
 * @property trackId Unique identifier for the movie.
 * @property trackName Name of the movie.
 * @property previewUrl URL for previewing the movie.
 * @property artworkUrl100 URL for the movie's artwork.
 * @property trackPrice Price of the movie.
 * @property trackRentalPrice Rental price of the movie.
 * @property releaseDate Release date of the movie.
 * @property primaryGenreName Primary genre name of the movie.
 * @property contentAdvisoryRating Advisory rating for the movie's content.
 * @property longDescription Full description of the movie.
 */
@Parcelize
data class MovieDetails(
    var trackId: Long? = 0,
    var trackName: String? = null,
    var previewUrl: String? = null,
    var artworkUrl100: String? = null,
    var trackPrice: Double? = 0.0,
    var trackRentalPrice: Double? = 0.0,
    var releaseDate: String? = null,
    var primaryGenreName: String? = null,
    var contentAdvisoryRating: String? = null,
    var longDescription: String? = null,
) : Parcelable {

    /**
     * Convert [MovieDetails] to its equivalent [MovieDetailsEntity].
     * @return [MovieDetailsEntity] with the same data.
     */
    fun toEntity(): MovieDetailsEntity {
        return MovieDetailsEntity(
            trackId = trackId,
            trackName = trackName,
            previewUrl = previewUrl,
            artworkUrl100 = artworkUrl100,
            trackPrice = trackPrice,
            trackRentalPrice = trackRentalPrice,
            releaseDate = releaseDate,
            primaryGenreName = primaryGenreName,
            contentAdvisoryRating = contentAdvisoryRating,
            longDescription = longDescription
        )
    }

    /**
     * Convert [MovieDetails] to its equivalent [SavedDataEntity].
     * @return [SavedDataEntity] with the same data.
     */
    fun toSavedEntity(): SavedDataEntity {
        return SavedDataEntity(
            trackId = trackId,
            trackName = trackName,
            previewUrl = previewUrl,
            artworkUrl100 = artworkUrl100,
            trackPrice = trackPrice,
            trackRentalPrice = trackRentalPrice,
            releaseDate = releaseDate,
            primaryGenreName = primaryGenreName,
            contentAdvisoryRating = contentAdvisoryRating,
            longDescription = longDescription,
        )
    }
}

