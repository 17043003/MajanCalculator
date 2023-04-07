package com.ishzk.android.majancalculator.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LongClickedState(
    var hand: Tile?,
    var isClicked: Boolean = false,
)

@HiltViewModel
class WaitHandViewModel @Inject constructor(private val repository: WaitHandRepository) : ViewModel() {
    val handTiles = MutableLiveData<HandTiles>()
    val waitTiles = MutableLiveData<List<String>>()

    val longClicked = MutableStateFlow(LongClickedState(null, false))

    fun onClickImageButton(view: View){
        val id = getIDString(view.id)
        Log.d(TAG, "clicked $id")

        if(handTiles.value == null){
            val handTile = HandTiles(CloseTiles(id), mutableListOf())
            handTiles.postValue(handTile)
        }else{
            handTiles.value?.add(id)
            handTiles.postValue(handTiles.value)
        }
        Log.d(TAG, "CloseTiles: ${CloseTiles(id)}")
    }

    fun onLongClickImageButton(viewID: Int): Boolean{
        val hand = getIDString(viewID)
        Log.d(WaitHandFragment.TAG, "$hand long tapped.")
        longClicked.value = LongClickedState(Tile(hand), true)
        return true
    }

    fun onSelectOpenHand(openKind: OpenTile){
        if(handTiles.value == null){
            val handTile = HandTiles(CloseTiles(""), mutableListOf(openKind))
            handTiles.postValue(handTile)
        }else{
            handTiles.value?.add(openKind)
            handTiles.postValue(handTiles.value)
        }
    }

    fun onClickOpenHand(openTile: OpenTile){
        handTiles.value?.openTiles?.remove(openTile)
        handTiles.postValue(handTiles.value)
    }

    fun onClickResult(){
        viewModelScope.launch(Dispatchers.IO) {
            val request = handTiles.value?.toWaitHandRequest() ?: return@launch
            repository.fetchWaitHand(request).collect { result ->
                waitTiles.postValue(result.winHands.map {
                    it.tile.reversed().replace('z', 'h') // return z as honors.
                })
            }
        }
    }

    fun getIDString(viewID: Int): String = when(viewID){
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

    fun getDrawableID(id: String): Int {
        return when(id) {
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

    companion object {
        const val TAG = "WaitHandViewModel"
    }
}