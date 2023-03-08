package com.ishzk.android.majancalculator.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ishzk.android.majancalculator.domain.FannFu
import com.ishzk.android.majancalculator.domain.WinnerParent

class CalculatorViewModel: ViewModel() {
    val fann = MutableLiveData<Int>(0)
    val fanns = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)

    val fu = MutableLiveData<Int>(0)
    val fus = listOf(20, 25) + (30..110 step 10)

    private val _calculateResult = MutableLiveData(0)
    val calculateResult: LiveData<Int> = _calculateResult

    private fun getFannFu(): FannFu{
        val fann = fanns[fann.value ?: 0]
        val fu = fus[fu.value ?: 0]
        return FannFu(fann, fu)
    }

    fun onClickResultButton(){
        val winner = WinnerParent()
        val tsumoPoints = winner.receiveTsumoPoints(getFannFu())
        _calculateResult.postValue(tsumoPoints.childPayment)
        Log.d(TAG, "${fann.value}-${fu.value} = ${tsumoPoints.childPayment}")
    }

    companion object{
        const val TAG = "CalculatorViewModel"
    }
}