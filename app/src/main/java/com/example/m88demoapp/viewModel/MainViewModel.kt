package com.example.m88demoapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.m88demoapp.repository.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: PreferenceRepository) : ViewModel() {

    /**
     * Suspended function to save the timestamp of the last visit time in a background thread.
     *
     * @param time The timestamp representing the last visit time to be saved.
     */
    suspend fun saveLastVisitTime(time: Long) {
        // Execute the saving operation in the background using IO dispatcher
        withContext(Dispatchers.IO) {
            repository.saveLastVisitTime(time)
        }
    }
}