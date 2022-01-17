package com.juarez.upaxdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juarez.upaxdemo.R
import com.juarez.upaxdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var moviesFragment: MoviesFragment
    private lateinit var mapFragment: MapFragment
    private lateinit var photoFragment: PhotoFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesFragment = MoviesFragment.newInstance()
        mapFragment = MapFragment.newInstance("", "")
        photoFragment = PhotoFragment.newInstance("", "")
        // add default fragment
        binding.bottomNavigationView.selectedItemId = R.id.action_movies
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, moviesFragment)
            commit()
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_movies -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, moviesFragment)
                        commit()
                    }
                }
                R.id.action_map -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, mapFragment)
                        commit()
                    }
                }
                R.id.action_photo -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, photoFragment)
                        commit()
                    }
                }
            }
            true
        }
    }
}