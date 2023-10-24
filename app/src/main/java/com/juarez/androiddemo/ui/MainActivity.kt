package com.juarez.androiddemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.juarez.androiddemo.R
import com.juarez.androiddemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.moviesFragment, R.id.mapFragment, R.id.photoFragment)
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.isVisible = destination.id != R.id.movieDetailFragment
        }
        navView.setupWithNavController(navController)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}