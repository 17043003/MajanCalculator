package com.ishzk.android.majancalculator.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.domain.FannFu
import com.ishzk.android.majancalculator.domain.OpenTile
import com.ishzk.android.majancalculator.domain.TileKind
import com.ishzk.android.majancalculator.domain.WinnerChild
import com.ishzk.android.majancalculator.domain.WinnerParent
import com.ishzk.android.majancalculator.domain.handpoint.CloseTiles
import com.ishzk.android.majancalculator.domain.handpoint.PointRepository
import com.ishzk.android.majancalculator.domain.handpoint.PointRequestData
import com.ishzk.android.majancalculator.domain.handpoint.ResultHandPoint
import com.ishzk.android.majancalculator.domain.handpoint.Tile
import com.ishzk.android.majancalculator.domain.handpoint.WonHand
import com.ishzk.android.majancalculator.domain.handpoint.YakuDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.IllegalArgumentException

@HiltViewModel
class HandPointViewModel @Inject constructor(private val repository: PointRepository): ViewModel() {

    private val _wonHand = MutableStateFlow<WonHand?>(null)

    private val _closeTiles = MutableStateFlow(CloseTiles())
    val closeTiles = _closeTiles.asStateFlow()

    private val _openTiles = MutableStateFlow(listOf<OpenTile>())
    val openTiles = _openTiles.asStateFlow()

    private val _selectedOpen = MutableStateFlow<Opens?>(null)

    private val _isSelectingWon = MutableStateFlow(false)
    val isSelectingWon = _isSelectingWon.asStateFlow()

    private val _wonTile = MutableStateFlow<Tile?>(null)
    val wonTile = _wonTile.asStateFlow()

    val ron = MutableStateFlow(false)
    val dora = MutableStateFlow(0)
    val doraEntries = (0..12).map { "    $it    " }

    val fieldWind = MutableStateFlow(0)
    val ownWind = MutableStateFlow(0)
    val winds = listOf("東", "南", "西", "北")

    val reachCheck = MutableStateFlow(false)
    val doubleReachCheck = MutableStateFlow(false)
    val oneShotCheck = MutableStateFlow(false)

    val haiteiCheck = MutableStateFlow(false)
    val rinshanCheck = MutableStateFlow(false)
    val chankanCheck = MutableStateFlow(false)

    val isMenzen = MutableStateFlow(true)
    val canRinshan = MutableStateFlow(false)

    private val _resultHandPoint: MutableStateFlow<ResultHandPoint?> = MutableStateFlow(null)
    val resultHandPoint = _resultHandPoint.asStateFlow()

    enum class Opens{
        CHI,
        PON,
        ANKAN,
        MINKAN,
    }


    fun onClickImageButton(view: View){
        viewModelScope.launch {
            val tileString = getIDString(view.id)
            Log.d("HandPointViewModel", "Clicked $tileString")
            val tile = Tile(tileString.first().toString(), tileString.last().digitToInt())
            if(_isSelectingWon.value){
                setWonTile(tile)
                return@launch
            }

            if(_selectedOpen.value == null) {
                setCloseTile(tile)
            }else{
                setOpenTile(tile)
            }
        }
    }

    fun onClickSelectedCloseTile(tile: Tile){
        _closeTiles.value = closeTiles.value.remove(tile)
    }

    fun onClickSelectedOpenTile(openTile: OpenTile){
        _openTiles.value = _openTiles.value.filterNot { it == openTile }
    }

    fun onSelectedOpenToggle(kind: Opens?){
        _selectedOpen.value = kind
    }

    fun onClickWonRect(){
        _isSelectingWon.value = !_isSelectingWon.value
    }

    suspend fun onClickResult() = withContext(Dispatchers.Default){
        val wonHand = _wonHand.value
        wonHand ?: return@withContext

        fun Boolean.toInt(): Int = if(this) 1 else 0
        val yakus = listOf(reachCheck.value, doubleReachCheck.value, oneShotCheck.value, rinshanCheck.value, haiteiCheck.value, chankanCheck.value)
            .map { it.toInt() }.joinToString("")

        val request = wonHand.toRequest(ownWind.value, fieldWind.value, !ron.value, yakus) ?: return@withContext
        repository.fetchPoint(request).collect{
            val fannFu = FannFu(it.han + dora.value, it.fu)

            val result = (if (ownWind.value == 0) WinnerParent() else WinnerChild())
                .let { winner ->
                    if (ron.value) winner.receiveRonPoint(fannFu)
                    else winner.receiveTsumoPoints(fannFu)
                }.toString()

            val yakuWithDora =  if(dora.value > 0) it.yaku + YakuDetail("dora", dora.value, dora.value, false) else it.yaku
            Log.d(TAG, "fan:${fannFu.fann}, fu:${fannFu.fu}")
            Log.d(TAG, "r:${result}, fu:${it.fuDetail}")

            yakuWithDora.forEach {
                Log.d(TAG, "name:${it.name}, han:${if(isMenzen.value) it.han_closed else it.han_open}")
            }

            _resultHandPoint.value = ResultHandPoint(fannFu.fann, fannFu.fu, result, yakuWithDora, it.fuDetail, isMenzen.value)
        }
    }

    fun consumeResultEvent(){
        _resultHandPoint.value = null
    }

    private fun setCloseTile(tile: Tile) {
        try {
            _wonHand.value = WonHand(
                closeTiles.value.add(tile),
                openTiles.value,
                wonTile.value
            )
        } catch (e: IllegalArgumentException) {
            return
        }

        _closeTiles.value = closeTiles.value.add(tile)
    }

    private fun setWonTile(tile: Tile) {
        try {
            _wonHand.value = WonHand(
                closeTiles.value,
                openTiles.value,
                tile
            )
        } catch (e: IllegalArgumentException) {
            return
        }

        _wonTile.value = tile
        _isSelectingWon.value = false
    }

    private fun setOpenTile(tile: Tile) {
        val tileKind = TileKind.getKind(tile.toString()) ?: return
        val openTile = when (_selectedOpen.value) {
            HandPointViewModel.Opens.CHI -> try {
                OpenTile.Chi(
                    tileKind,
                    listOf(tile.number, tile.number + 1, tile.number + 2).joinToString("")
                )
            } catch (e: IllegalArgumentException) {
                return
            }

            HandPointViewModel.Opens.PON -> OpenTile.Pon(
                tileKind,
                listOf(tile.number, tile.number, tile.number).joinToString("")
            )

            HandPointViewModel.Opens.ANKAN -> OpenTile.Kan(
                tileKind,
                listOf(tile.number, tile.number, tile.number, tile.number).joinToString(""),
                true
            )

            HandPointViewModel.Opens.MINKAN -> OpenTile.Kan(
                tileKind,
                listOf(tile.number, tile.number, tile.number, tile.number).joinToString(""),
                false
            )

            else -> return
        }

        try {
            _wonHand.value = WonHand(
                closeTiles.value,
                openTiles.value + openTile,
                wonTile.value
            )

        } catch (e: IllegalArgumentException) {
            return
        }

        _openTiles.value = openTiles.value + openTile
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

    companion object{
        const val TAG = "HandPointViewModel"
    }
}