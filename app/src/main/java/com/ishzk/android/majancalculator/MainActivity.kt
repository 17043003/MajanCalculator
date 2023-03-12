package com.ishzk.android.majancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ishzk.android.majancalculator.databinding.ActivityMainBinding
import com.ishzk.android.majancalculator.domain.PointRequestData
import com.ishzk.android.majancalculator.domain.PointViewModel
import com.ishzk.android.majancalculator.domain.TileKinds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: PointViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        setDrawerMenu()

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val request = PointRequestData(
                    TileKinds(man = "11112345678999"),
                    TileKinds(man = "1"),
                    TileKinds(pin = "1"),
                    true
                )

                viewModel.fetchPoint(request).collect {
                    Log.d(TAG, "point:${it.total}")
                }
            }
        }
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