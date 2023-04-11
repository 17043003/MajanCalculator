package com.ishzk.android.majancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ishzk.android.majancalculator.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        setDrawerMenu()
    }

    // Setting navigation drawer to fragment transition.
    private fun setDrawerMenu(){
        val toggle = ActionBarDrawerToggle(
            this, binding.root, binding.mainToolbar, R.string.app_name, R.string.app_name)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()

        val navController = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.root)
        binding.mainNavView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}