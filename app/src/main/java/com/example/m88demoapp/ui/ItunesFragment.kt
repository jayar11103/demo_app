package com.example.m88demoapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m88demoapp.R
import com.example.m88demoapp.actionHandler.MovieActionHandler
import com.example.m88demoapp.adapter.MovieAdapter
import com.example.m88demoapp.databinding.FragmentItunesBinding
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.model.MovieDetails
import com.example.m88demoapp.util.extractDate
import com.example.m88demoapp.viewModel.ItunesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItunesFragment : Fragment(), MovieActionHandler {

    // Late-initialized binding for the fragment layout
    private lateinit var binding: FragmentItunesBinding

    // ViewModel instance for managing data related to iTunes
    private val itunesViewModel by viewModel<ItunesViewModel>()

    // Adapter for displaying a list of movies
    private val movieAdapter by lazy { MovieAdapter(emptyList(), this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the fragment layout using ViewBinding
        binding = FragmentItunesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView to display movies
        setupRecyclerView()

        itunesViewModel.fetchMovie()

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshMovie()
        }

        // Check and navigate to the last screen visited
        lifecycleScope.launch {
            val lastScreen = itunesViewModel.getLastScreen()

            when (lastScreen) {
                R.id.ItunesFavoriteFragment -> {
                    findNavController().navigate(lastScreen)
                }
                R.id.ItunesMovieDetailFragment -> {
                    findNavController().navigate(lastScreen)
                }
            }
        }

        // Check if there is an active network connection
        if (isNetworkConnected()) {
            binding.itError.visibility = View.GONE
        } else {
            // Handle case when there is no network connectivity
            binding.itContainer.visibility = View.GONE
            binding.itError.visibility = View.VISIBLE
            binding.itError.text = getString(R.string.no_internet_connectivity)
        }

        // Observe changes in the movie list from the ViewModel
        itunesViewModel.movieList.observe(viewLifecycleOwner) { movies ->
            // Update the adapter with the latest movie list
            movieAdapter.updateMovies(movies)
        }

        // Listen for text changes in the search EditText and update search results
        itunesViewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
            // Update the adapter with the latest search results

            if (searchResults.isEmpty()) {
                binding.itError.visibility = View.VISIBLE
                binding.itError.text = getText(R.string.no_movie_found)
            } else binding.itError.visibility = View.GONE

            movieAdapter.updateMovies(searchResults)
        }

        // Listen for text changes in the search EditText and trigger movie search
        binding.itSearch.doOnTextChanged { text, start, before, count ->
            itunesViewModel.searchMoviesByText(text.toString())
        }

        // Load last visit time and display it if available
        lifecycleScope.launch {
            itunesViewModel.getSavedMovies()
            val lastVisitTimeMillis = itunesViewModel.getLastVisitTime().extractDate()
            if (lastVisitTimeMillis.isEmpty()) {
                binding.itRecentVisit.visibility = View.GONE
            } else {
                binding.itRecentVisit.visibility = View.VISIBLE
                binding.date = lastVisitTimeMillis
            }
        }
    }

    private fun refreshMovie(){
        binding.rvMovieList.visibility = View.GONE
        Handler().postDelayed({
            binding.rvMovieList.visibility = View.VISIBLE
            itunesViewModel.fetchMovie()
            binding.swipeRefreshLayout.isRefreshing = false
        }, 2000) // 3-second delay
    }

    // Helper function to check if there is an active network connection
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    // Set up the RecyclerView with the movie adapter
    private fun setupRecyclerView() {
        binding.rvMovieList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovieList.layoutManager =
            GridLayoutManager(requireContext(), 1)
        binding.rvMovieList.adapter = movieAdapter
    }

    override fun onMovieItemClick(item: MovieDetails) {
        // Save temporary movie details and navigate to the movie detail screen
        lifecycleScope.launch {
            itunesViewModel.savedTemporaryMovieDetail(item.toSavedEntity())
        }
        findNavController().navigate(R.id.ItunesMovieDetailFragment)
    }

    override fun onMovieItemSave(item: MovieDetails) {
        // Save the movie details to the ViewModel
        val movieDetailsEntity = item.toEntity()
        lifecycleScope.launch {
            itunesViewModel.saveMovieDetails(movieDetailsEntity)
            itunesViewModel.getSavedMovies()
        }
    }

    override fun getSavedMovies(): List<MovieDetailsEntity>? {
        // Retrieve the list of saved movies from the ViewModel
        return itunesViewModel.savedMovies.value
    }

    override fun deleteSavedMovies(trackId: Long?) {
        // Delete a saved movie with the specified track ID
        lifecycleScope.launch {
            itunesViewModel.deleteSavedMovie(trackId)
            itunesViewModel.getSavedMovies()
        }
    }

    override fun onStop() {
        super.onStop()
        // Save the last visited screen when the fragment is stopped
        lifecycleScope.launch {
            itunesViewModel.saveLastScreen(getCurrentScreenId())
        }
    }

    // Helper function to get the ID of the current screen from the navigation controller
    private fun getCurrentScreenId(): Int {
        return findNavController().currentDestination?.id ?: 0
    }
}

