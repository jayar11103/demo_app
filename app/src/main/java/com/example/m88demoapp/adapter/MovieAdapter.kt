package com.example.m88demoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.m88demoapp.actionHandler.MovieActionHandler
import com.example.m88demoapp.R
import com.example.m88demoapp.databinding.LayoutMovieListItemBinding
import com.example.m88demoapp.model.MovieDetails

/**
 * RecyclerView Adapter for displaying a list of movies.
 *
 * @param list The list of [MovieDetails] to be displayed.
 * @param listener The [MovieActionHandler] to handle interactions with the movie items.
 */
class MovieAdapter(private var list: List<MovieDetails>, private val listener: MovieActionHandler) :
    RecyclerView.Adapter<MovieViewHolder>() {

    /**
     * Updates the adapter's movie list with a new list and notifies the changes.
     *
     * @param newList The new list of [MovieDetails] to be displayed.
     */
    fun updateMovies(newList: List<MovieDetails>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: LayoutMovieListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.layout_movie_list_item, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        // Bind the movie details to the ViewHolder
        holder.bind(list[position], listener)
    }
}

