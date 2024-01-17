package com.example.m88demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.m88demoapp.databinding.ActivityMainBinding
import com.example.m88demoapp.viewModel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /**
         * Initializes Koin dependency injection framework for the application.
         * It sets up Koin with the Android application context and the specified modules.
         * In this case, it uses the [apiModule] to define the dependencies.
         *
         * @see apiModule
         */

        startKoin {
            androidContext(this@MainActivity)
            modules(apiModule)
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this // Important for LiveData to observe changes

// Get the NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up BottomNavigationView with NavController
        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.ItunesFragment)
                    true
                }
                R.id.navigation_saved -> {
                    navController.navigate(R.id.ItunesFavoriteFragment)
                    true
                }
                else -> false
            }
        }

        // Add a listener for navigation events
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("Navigation", "Navigated to destination: ${destination.label}")
        }
    }

    override fun onStop() {
        super.onStop()
        // This block of code will be executed when the app is destroyed or closed
        lifecycleScope.launch {
            val currentTime = System.currentTimeMillis()
            viewModel.saveLastVisitTime(currentTime)
        }
    }
}
