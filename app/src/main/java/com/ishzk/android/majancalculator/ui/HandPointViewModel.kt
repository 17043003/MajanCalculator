package com.ishzk.android.majancalculator.ui

import androidx.lifecycle.ViewModel
import com.ishzk.android.majancalculator.domain.handpoint.PointRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HandPointViewModel @Inject constructor(private val repository: PointRepository): ViewModel() {
}