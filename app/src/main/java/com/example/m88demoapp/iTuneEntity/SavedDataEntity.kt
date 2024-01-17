package com.example.m88demoapp.iTuneEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing saved data in the local database.
 *
 * @param id Unique identifier for the saved data (auto-generated).
 * @param trackId Unique identifier for the associated movie detail.
 * @param trackName The name of the associated track.
 * @param previewUrl The URL for previewing the associated track.
 * @param artworkUrl100 The URL for the artwork (image) associated with the track.
 * @param trackPrice The price of the associated track.
 * @param trackRentalPrice The rental price of the associated track.
 * @param releaseDate The release date of the associated track.
 * @param primaryGenreName The primary genre name of the associated track.
 * @param contentAdvisoryRating The content advisory rating of the associated track.
 * @param longDescription The long description of the associated track.
 */
@Entity(tableName = "saved_data_table")
data class SavedDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val trackId: Long?,
    val trackName: String?,
    val previewUrl: String?,
    val artworkUrl100: String?,
    val trackPrice: Double?,
    val trackRentalPrice: Double?,
    val releaseDate: String?,
    val primaryGenreName: String?,
    val contentAdvisoryRating: String?,
    val longDescription: String?,
)
