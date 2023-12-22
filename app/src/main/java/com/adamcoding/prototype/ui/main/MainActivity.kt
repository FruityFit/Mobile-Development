package com.adamcoding.prototype.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.adamcoding.prototype.R
import com.adamcoding.prototype.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHomeFragment = supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment
        val navController = navHomeFragment.navController

        binding.btmNav.setupWithNavController(navController)
    }
}