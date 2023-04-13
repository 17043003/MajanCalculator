package com.ishzk.android.majancalculator.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.domain.handpoint.CloseTiles
import com.ishzk.android.majancalculator.domain.handpoint.PointRepository
import com.ishzk.android.majancalculator.domain.handpoint.Tile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HandPointViewModel @Inject constructor(private val repository: PointRepository): ViewModel() {

    private val _closeTiles = MutableStateFlow(CloseTiles())
    val closeTiles = _closeTiles.asStateFlow()
    fun onClickImageButton(view: View){
        viewModelScope.launch {
            val tileString = getIDString(view.id)
            Log.d("HandPointViewModel", "Clicked $tileString")
            val tile = Tile(tileString.first().toString(), tileString.last().digitToInt())
            _closeTiles.value = closeTiles.value.add(tile)
        }
    }

    fun getIDString(viewID: Int): String = when(viewID){
        R.id.man1 -> "m1"
        R.id.man2 -> "m2"
        R.id.man3 -> "m3"
        R.id.man4 -> "m4"
        R.id.man5 -> "m5"
        R.id.man6 -> "m6"
        R.id.man7 -> "m7"
        R.id.man8 -> "m8"
        R.id.man9 -> "m9"

        R.id.sou1 -> "s1"
        R.id.sou2 -> "s2"
        R.id.sou3 -> "s3"
        R.id.sou4 -> "s4"
        R.id.sou5 -> "s5"
        R.id.sou6 -> "s6"
        R.id.sou7 -> "s7"
        R.id.sou8 -> "s8"
        R.id.sou9 -> "s9"

        R.id.pin1 -> "p1"
        R.id.pin2 -> "p2"
        R.id.pin3 -> "p3"
        R.id.pin4 -> "p4"
        R.id.pin5 -> "p5"
        R.id.pin6 -> "p6"
        R.id.pin7 -> "p7"
        R.id.pin8 -> "p8"
        R.id.pin9 -> "p9"

        R.id.honors1 -> "h1"
        R.id.honors2 -> "h2"
        R.id.honors3 -> "h3"
        R.id.honors4 -> "h4"
        R.id.honors5 -> "h5"
        R.id.honors6 -> "h6"
        R.id.honors7 -> "h7"
        else -> ""
    }

    fun getDrawableID(id: String): Int = when (id) {
        "m1" -> R.drawable.m1
        "m2" -> R.drawable.m2
        "m3" -> R.drawable.m3
        "m4" -> R.drawable.m4
        "m5" -> R.drawable.m5
        "m6" -> R.drawable.m6
        "m7" -> R.drawable.m7
        "m8" -> R.drawable.m8
        "m9" -> R.drawable.m9

        "s1" -> R.drawable.s1
        "s2" -> R.drawable.s2
        "s3" -> R.drawable.s3
        "s4" -> R.drawable.s4
        "s5" -> R.drawable.s5
        "s6" -> R.drawable.s6
        "s7" -> R.drawable.s7
        "s8" -> R.drawable.s8
        "s9" -> R.drawable.s9

        "p1" -> R.drawable.p1
        "p2" -> R.drawable.p2
        "p3" -> R.drawable.p3
        "p4" -> R.drawable.p4
        "p5" -> R.drawable.p5
        "p6" -> R.drawable.p6
        "p7" -> R.drawable.p7
        "p8" -> R.drawable.p8
        "p9" -> R.drawable.p9

        "h1" -> R.drawable.h1
        "h2" -> R.drawable.h2
        "h3" -> R.drawable.h3
        "h4" -> R.drawable.h4
        "h5" -> R.drawable.h5
        "h6" -> R.drawable.h6
        "h7" -> R.drawable.h7
        else -> 0
    }
}