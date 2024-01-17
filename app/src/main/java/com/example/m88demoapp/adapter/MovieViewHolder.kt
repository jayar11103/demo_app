package com.example.m88demoapp.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.m88demoapp.R
import com.example.m88demoapp.actionHandler.MovieActionHandler
import com.example.m88demoapp.databinding.LayoutMovieListItemBinding
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.model.MovieDetails
import com.example.m88demoapp.util.extractYearFromDate

/**
 * ViewHolder for displaying individual movie items in a RecyclerView.
 *
 * @param binding The ViewBinding for the movie item layout.
 */
class MovieViewHolder(private val binding: LayoutMovieListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds the [movieDetails] to the ViewHolder and sets up click and interaction listeners.
     *
     * @param movieDetails The details of the movie to be displayed.
     * @param listener The [MovieActionHandler] to handle interactions with the movie item.
     */
    fun bind(
        movieDetails: MovieDetails,
        listener: MovieActionHandler
    ) {
        with(binding) {
            // Bind movie details to the layout
            movie = movieDetails

            // Display the release date and artwork
            itReleaseDate.text = movieDetails.releaseDate?.extractYearFromDate()
            itArtwork.load(movieDetails.artworkUrl100) {
                placeholder(R.drawable.no_image)
            }

            // Set up a click listener for the entire item
            root.setOnClickListener {
                listener.onMovieItemClick(movieDetails)
            }
        }
    }
}

