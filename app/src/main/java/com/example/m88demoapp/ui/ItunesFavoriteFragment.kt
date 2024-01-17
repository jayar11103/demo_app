package com.example.m88demoapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m88demoapp.R
import com.example.m88demoapp.actionHandler.MovieActionHandler
import com.example.m88demoapp.adapter.FavoriteMovieAdapter
import com.example.m88demoapp.databinding.FragmentItunesFavoriteBinding
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.model.MovieDetails
import com.example.m88demoapp.viewModel.ItunesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItunesFavoriteFragment: Fragment(), MovieActionHandler {

    private val itunesViewModel by viewModel<ItunesViewModel>()
    private lateinit var binding: FragmentItunesFavoriteBinding
    private val favoriteMovieAdapter by lazy { FavoriteMovieAdapter(emptyList(), this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItunesFavoriteBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            itunesViewModel.getSavedMovies()
            itunesViewModel.savedMovies.value?.let { favoriteMovieAdapter.savedMovies(it) }

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.itBack.setOnClickListener { findNavController().navigate(R.id.ItunesFragment) }
    }

    private fun setupRecyclerView(){
        with(binding){
            rvFavoriteMovieList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvFavoriteMovieList.layoutManager =
                GridLayoutManager(requireContext(), 1)
            rvFavoriteMovieList.adapter = favoriteMovieAdapter
        }
    }

    override fun onMovieItemClick(item: MovieDetails) {

    }

    override fun onMovieItemSave(item: MovieDetails) {

    }

    override fun getSavedMovies(): List<MovieDetailsEntity>? {
        return itunesViewModel.savedMovies.value
    }

    override fun deleteSavedMovies(trackId: Long?) {
        lifecycleScope.launch {
            // Suspend the coroutine until the delete operation is completed
            itunesViewModel.deleteSavedMovie(trackId)

            delay(500)
                // Get the updated list of saved movies
                itunesViewModel.getSavedMovies()
                Log.d("lastest list: ", itunesViewModel.savedMovies.value?.size.toString())
                // Update the adapter with the latest data
                itunesViewModel.savedMovies.value?.let { favoriteMovieAdapter.savedMovies(it) }
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch{
            itunesViewModel.saveLastScreen(getCurrentScreenId())
        }
    }

    private fun getCurrentScreenId(): Int {
        return findNavController().currentDestination?.id ?: 0
    }
}
