package com.ishzk.android.majancalculator.domain

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(private val repository: PointRepository): ViewModel() {

    suspend fun fetchPoint(requestData: PointRequestData): Flow<PointResponseData> {
        return repository.fetchPoint(requestData)
    }
}