package com.ishzk.android.majancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ishzk.android.majancalculator.domain.PointRequestData
import com.ishzk.android.majancalculator.domain.PointViewModel
import com.ishzk.android.majancalculator.domain.TileKinds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: PointViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    companion object {
        const val TAG = "MainActivity"
    }
}