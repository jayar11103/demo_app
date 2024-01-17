package com.example.m88demoapp

import androidx.room.Room
import com.example.m88demoapp.appDatabase.AppDatabase
import com.example.m88demoapp.repository.ItunesRepository
import com.example.m88demoapp.repository.ItunesRepositoryImpl
import com.example.m88demoapp.repository.PreferenceRepository
import com.example.m88demoapp.repository.PreferenceRepositoryImpl
import com.example.m88demoapp.service.ItunesApiService
import com.example.m88demoapp.viewModel.ItunesViewModel
import com.example.m88demoapp.viewModel.ItunesViewModelFactory
import com.example.m88demoapp.viewModel.MainViewModel
import com.example.m88demoapp.viewModel.MainViewModelFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {

    // Database setup
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    // DAOs
    single { get<AppDatabase>().movieDetailsDao() }
    single { get<AppDatabase>().savedDataDao() }

    // Retrofit API service
    single { ItunesApiService.create() }

    // Repository and ViewModel dependencies
    single<PreferenceRepository> { PreferenceRepositoryImpl(get()) }
    single<ItunesRepository> { ItunesRepositoryImpl(get(), get(), get()) }

    // ViewModel factories
    factory { MainViewModelFactory(get()) }
    factory { ItunesViewModelFactory(get(), get()) }

    // ViewModels
    viewModel { MainViewModel(get()) }
    viewModel { ItunesViewModel(get(), get()) }
}
