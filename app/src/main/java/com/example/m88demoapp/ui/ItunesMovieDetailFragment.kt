package com.example.m88demoapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.m88demoapp.R
import com.example.m88demoapp.databinding.FragmentItunesMovieDetailBinding
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.model.MovieDetails
import com.example.m88demoapp.util.extractYearFromDate
import com.example.m88demoapp.viewModel.ItunesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItunesMovieDetailFragment : Fragment() {

    private val itunesViewModel by viewModel<ItunesViewModel>()
    private lateinit var binding: FragmentItunesMovieDetailBinding
    private var movieDetails = MovieDetails()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItunesMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleScope.launch {
                itunesViewModel.getSavedTemporaryMovies()
                val movieDetail = itunesViewModel.savedTemporaryMovies.value

                movieDetails.apply {

                    trackId = movieDetail?.trackId
                    trackName = movieDetail?.trackName
                    previewUrl = movieDetail?.previewUrl
                    artworkUrl100 = movieDetail?.artworkUrl100
                    trackPrice = movieDetail?.trackPrice
                    trackRentalPrice = movieDetail?.trackRentalPrice
                    releaseDate = movieDetail?.releaseDate
                    primaryGenreName = movieDetail?.primaryGenreName
                    contentAdvisoryRating = movieDetail?.contentAdvisoryRating
                    longDescription = movieDetail?.longDescription

                }
                movie = movieDetails
                itReleaseDate.text = movieDetail?.releaseDate?.extractYearFromDate()

                itBack.setOnClickListener {
                    findNavController().navigate(R.id.ItunesFragment)
                    lifecycleScope.launch {
                        itunesViewModel.deleteSavedMovie(movieDetail!!)
                    }
                }

                lifecycleScope.launch {
                    itunesViewModel.getSavedMovies()

                    val isSaved =
                        isMovieDetailsSaved(movieDetails, itunesViewModel.savedMovies.value)
                    itSave.isChecked = isSaved

                    itSave.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            saveToFavorite(movieDetails.toEntity())
                        } else {
                            deleteFavorite(movieDetails.trackId)
                        }
                        updateSaveIcon(isChecked)
                    }
                }

                if (isNetworkConnected()) {
                    playVideo(movieDetails.previewUrl.toString())
                } else {
                    videoView.visibility = View.GONE
                    itArtwork.visibility = View.VISIBLE
                    itArtwork.load(movieDetails.artworkUrl100) {
                        placeholder(R.drawable.no_image)
                    }
                }
            }
        }
    }

    // check the network status and return an boolean base on it
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    private fun saveToFavorite(movie: MovieDetailsEntity) {
        // save to favorite
        lifecycleScope.launch {
            itunesViewModel.saveMovieDetails(movie)
        }
    }

    private fun deleteFavorite(trackId: Long?) {
        // remove to favorite
        lifecycleScope.launch {
            itunesViewModel.deleteSavedMovie(trackId)
        }
    }

    private fun playVideo(videoUrl: String) {
        val videoUri = Uri.parse(videoUrl)
        with(binding) {
            videoView.setVideoURI(videoUri)

            videoView.setOnCompletionListener { mp ->
                // Loop the video when it finishes
                mp.start()
            }

            videoView.setOnPreparedListener { mp ->
                // Start playing the video when it's prepared
                mp.start()
            }
        }
    }

    private fun updateSaveIcon(isSaved: Boolean) {
        // saved and update the icon of Save Button base on status if its saved or not
        binding.itSave.setBackgroundResource(if (isSaved) R.drawable.fav_yellow else R.drawable.fav_grey)
    }

    private fun isMovieDetailsSaved(
        movieDetails: MovieDetails,
        savedMovies: List<MovieDetailsEntity>?
    ): Boolean {
        // Check if the movieDetails exists in the saved movies list
        return savedMovies?.any { it.trackId == movieDetails.trackId } ?: false
    }

    override fun onStop() {
        super.onStop()
        // save the current screen
        lifecycleScope.launch{
            itunesViewModel.saveLastScreen(getCurrentScreenId())
        }
    }

    private fun getCurrentScreenId(): Int {
        return findNavController().currentDestination?.id ?: 0
    }
}
