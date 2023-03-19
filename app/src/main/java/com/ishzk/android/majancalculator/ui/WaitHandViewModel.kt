package com.ishzk.android.majancalculator.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.domain.CloseTiles
import com.ishzk.android.majancalculator.domain.OpenTile
import com.ishzk.android.majancalculator.domain.WinTile

class WaitHandViewModel : ViewModel() {
    val closeTiles = MutableLiveData<CloseTiles>()
    val openTiles = MutableLiveData<List<OpenTile>>()
    val winTile = MutableLiveData<WinTile>()

    fun onClickImageButton(view: View){
        val id = getIDString(view.id)
        Log.d(TAG, "clicked $id")

        val newTiles = CloseTiles(if(closeTiles.value == null) "" else closeTiles.value.toString())
        newTiles.add(id)
        closeTiles.postValue(newTiles)
        Log.d(TAG, "CloseTiles: $newTiles")
    }

    private fun getIDString(viewID: Int): String = when(viewID){
        R.id.m1 -> "m1"
        R.id.m2 -> "m2"
        R.id.m3 -> "m3"
        R.id.m4 -> "m4"
        R.id.m5 -> "m5"
        R.id.m6 -> "m6"
        R.id.m7 -> "m7"
        R.id.m8 -> "m8"
        R.id.m9 -> "m9"

        R.id.s1 -> "s1"
        R.id.s2 -> "s2"
        R.id.s3 -> "s3"
        R.id.s4 -> "s4"
        R.id.s5 -> "s5"
        R.id.s6 -> "s6"
        R.id.s7 -> "s7"
        R.id.s8 -> "s8"
        R.id.s9 -> "s9"

        R.id.p1 -> "p1"
        R.id.p2 -> "p2"
        R.id.p3 -> "p3"
        R.id.p4 -> "p4"
        R.id.p5 -> "p5"
        R.id.p6 -> "p6"
        R.id.p7 -> "p7"
        R.id.p8 -> "p8"
        R.id.p9 -> "p9"

        R.id.h1 -> "h1"
        R.id.h2 -> "h2"
        R.id.h3 -> "h3"
        R.id.h4 -> "h4"
        R.id.h5 -> "h5"
        R.id.h6 -> "h6"
        R.id.h7 -> "h7"
        else -> ""
    }

    companion object {
        const val TAG = "WaitHandViewModel"
    }
}