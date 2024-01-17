package com.example.m88demoapp.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.m88demoapp.R
import com.example.m88demoapp.actionHandler.MovieActionHandler
import com.example.m88demoapp.databinding.LayoutFavoriteMovieListItemBinding
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.util.extractYearFromDate

/**
 * ViewHolder for displaying a favorite movie item in the RecyclerView.
 *
 * @param binding The [LayoutFavoriteMovieListItemBinding] associated with the ViewHolder.
 */
class FavoriteMovieViewHolder(private val binding: LayoutFavoriteMovieListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds the [movieDetails] to the ViewHolder and sets up click listeners.
     *
     * @param movieDetails The [MovieDetailsEntity] representing the favorite movie.
     * @param listener The [MovieActionHandler] to handle interactions with the movie item.
     */
    fun bind(
        movieDetails: MovieDetailsEntity,
        listener: MovieActionHandler
    ) {
        with(binding) {
            // Bind the movie details to the layout for data binding
            movie = movieDetails

            // Set up click listener to delete the saved movie
            itSave.setOnClickListener {
                listener.deleteSavedMovies(movieDetails.trackId)
            }

            // Set up click listener to expand or collapse the movie description
            itSeeMore.setOnClickListener {
                if (itDescription.maxLines == 3) {
                    // Expand to show full description
                    itDescription.maxLines = Int.MAX_VALUE
                    itSeeMore.text = itemView.context.getString(R.string.see_less)
                } else {
                    // Collapse to show only 3 lines
                    itDescription.maxLines = 3
                    itSeeMore.text = itemView.context.getString(R.string.see_more)
                }
            }

            // Display the release date and load the movie artwork
            itReleaseDate.text = movieDetails.releaseDate?.extractYearFromDate()
            itArtwork.load(movieDetails.artworkUrl100) {
                placeholder(R.drawable.no_image)
            }
        }
    }
}
