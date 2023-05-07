package com.ishzk.android.majancalculator.ui

import androidx.lifecycle.ViewModel
import com.ishzk.android.majancalculator.domain.handpoint.FuDetail
import com.ishzk.android.majancalculator.domain.handpoint.YakuDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PointResultViewModel : ViewModel() {
    private val _fann = MutableStateFlow(0)
    val fann: StateFlow<Int> = _fann.asStateFlow()

    private val _fu = MutableStateFlow(0)
    val fu: StateFlow<Int> = _fu.asStateFlow()

    private val _resultMessage = MutableStateFlow<String>("")
    val resultMessage: StateFlow<String> = _resultMessage.asStateFlow()

    private val _yakuDetails = MutableStateFlow<List<YakuDetail>>(listOf())
    val yakuDetails: StateFlow<List<YakuDetail>> = _yakuDetails.asStateFlow()

    private val _fuDetails = MutableStateFlow<List<FuDetail>>(listOf())
    val fuDetails: StateFlow<List<FuDetail>> = _fuDetails.asStateFlow()
}