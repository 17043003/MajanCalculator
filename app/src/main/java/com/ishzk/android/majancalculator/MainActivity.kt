package com.ishzk.android.majancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ishzk.android.majancalculator.domain.PointRepository
import com.ishzk.android.majancalculator.domain.PointRequestData
import com.ishzk.android.majancalculator.domain.TileKinds
import com.ishzk.android.majancalculator.repository.RetrofitPointRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val repository: PointRepository = RetrofitPointRepository()
                val request = PointRequestData(
                    TileKinds(man = "11112345678999"),
                    TileKinds(man = "1"),
                    TileKinds(pin = "1"),
                    true
                )
                repository.fetchPoint(request).collect {
                    Log.d(TAG, "point:${it.total}")
                }
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}