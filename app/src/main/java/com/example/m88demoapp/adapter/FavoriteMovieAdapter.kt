package com.example.m88demoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.m88demoapp.R
import com.example.m88demoapp.actionHandler.MovieActionHandler
import com.example.m88demoapp.databinding.LayoutFavoriteMovieListItemBinding
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity

/**
 * Adapter for displaying a list of favorite movies.
 *
 * @param list The list of [MovieDetailsEntity] representing favorite movies.
 * @param listener The [MovieActionHandler] to handle interactions with movie items.
 */
class FavoriteMovieAdapter(
    private var list: List<MovieDetailsEntity>,
    private val listener: MovieActionHandler
) :
    RecyclerView.Adapter<FavoriteMovieViewHolder>() {

    /**
     * Updates the list of favorite movies and notifies the adapter.
     *
     * @param saveList The updated list of [MovieDetailsEntity].
     */
    fun savedMovies(saveList: List<MovieDetailsEntity>) {
        list = saveList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        // Inflate the layout for each item in the RecyclerView
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: LayoutFavoriteMovieListItemBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_favorite_movie_list_item,
                parent,
                false
            )
        return FavoriteMovieViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        // Bind each movie item to its ViewHolder
        holder.bind(list[position], listener)
    }
}
