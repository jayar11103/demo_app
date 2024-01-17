package com.example.m88demoapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.m88demoapp.repository.ItunesRepository
import com.example.m88demoapp.repository.PreferenceRepository

/**
 * Factory class responsible for creating instances of the ItunesViewModel.
 * It implements the ViewModelProvider.Factory interface to facilitate ViewModel creation.
 *
 * @param repository The ItunesRepository instance used for fetching and managing iTunes-related data.
 * @param appRepository The PreferenceRepository instance used for app-related data operations.
 */
class ItunesViewModelFactory(private val repository: ItunesRepository, private val appRepository: PreferenceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItunesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItunesViewModel(repository, appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

