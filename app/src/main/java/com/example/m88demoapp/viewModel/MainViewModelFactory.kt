package com.example.m88demoapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.m88demoapp.repository.PreferenceRepositoryImpl


/**
 * Factory class responsible for creating instances of the MainViewModel.
 * It implements the ViewModelProvider.Factory interface to facilitate ViewModel creation.
 *
 * @param repository The PreferenceRepositoryImpl instance used for data operations.
 */
class MainViewModelFactory(private val repository: PreferenceRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
