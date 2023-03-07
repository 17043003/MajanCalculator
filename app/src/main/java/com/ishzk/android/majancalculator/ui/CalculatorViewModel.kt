package com.ishzk.android.majancalculator.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ishzk.android.majancalculator.domain.FannFu
import com.ishzk.android.majancalculator.domain.WinnerParent

class CalculatorViewModel: ViewModel() {
    private val _fann = MutableLiveData<Int>(1)
    val fann: LiveData<Int> = _fann

    private val _fu = MutableLiveData<Int>(20)
    val fu: LiveData<Int> = _fu

    private val _calculateResult = MutableLiveData(0)
    val calculateResult: LiveData<Int> = _calculateResult

    val fanns = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)
    val fus = listOf(20, 25) + (30..110 step 10)

    fun onClickResultButton(){
        val winner = WinnerParent()
        val tsumoPoints = winner.receiveTsumoPoints(FannFu(fann.value ?: 1, fu.value ?: 30))
        _calculateResult.postValue(tsumoPoints.childPayment)
        Log.d(TAG, "${fann.value}-${fu.value} = ${tsumoPoints.childPayment}")
    }

    companion object{
        const val TAG = "CalculatorViewModel"
    }
}